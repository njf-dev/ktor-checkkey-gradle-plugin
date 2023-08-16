package com.njf2016.plugin

import com.njf2016.plugin.utils.simple_yyyyMMddHHmmss
import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import io.ktor.network.tls.certificates.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.security.KeyStore
import java.security.cert.X509Certificate
import java.util.*

/**
 * 根据 `app/src/main/resources` 内的配置文件，检查自签 ssl 证书是否存在或过期。
 * 如果证书不存在或过期、或过期时间少于 60 天，则重新颁发。
 *
 * @author <a href=mailto:mcxinyu@foxmail.com>yuefeng</a> in 2023/2/3.
 */
class CheckKey : Plugin<Project> {
    private val TAG = CheckKey::class.java.name

    override fun apply(project: Project) {
        project.allprojects {
            val resources = this.file("src/main/resources")
            if (resources.isDirectory) {
                resources.listFiles { file ->
                    // 展开资源文件夹并过滤 *.conf 文件
                    file.isFile && file.name.endsWith(".conf")
                }?.forEach {
                    // 解析 *.conf
                    val ssl: SSL = ConfigFactory.parseFile(it).extract("ktor.security.ssl")

                    File(ssl.keyStore).let {
                        // 兼容打包和测试运行需要，证书存放位置相对项目根目录
                        if (it.exists() || it.isAbsolute) it else File(project.rootDir, it.path)
                    }.let { file ->
                        if (!file.exists()) {
                            val mkdirs = file.parentFile?.mkdirs()
                            mkdirs?.log("mkdirs ")

                            // 证书不存在，重新签名
                            "配置文件 ${it.name} 的证书不存在，重新签名。".log()
                            buildKeyStore(ssl, file)
                        } else {
                            // 解析证书
                            val keyStore = KeyStore.getInstance("JKS")
                            keyStore.load(file.inputStream(), ssl.keyStorePassword.toCharArray())
                            val notAfter = (keyStore.getCertificate(ssl.keyAlias) as X509Certificate).notAfter

                            // 过期时间少于 60 天，重新签名
                            val date30Day = Date(System.currentTimeMillis() + 60 * 24 * 60 * 60 * 1000L)
                            if (!notAfter.after(date30Day)) {
                                file.delete()
                                "配置文件 ${it.name} 的证书 ${simple_yyyyMMddHHmmss.format(notAfter)} 过期，重新签名。".log()
                                buildKeyStore(ssl, file)
                            }
                        }

                        "配置文件 ${it.name} 的证书 ${file.path} 检查完成。"
                    }.log()
                }
            }
        }
    }

    private fun buildKeyStore(ssl: SSL, file: File) {
        buildKeyStore {
            certificate(ssl.keyAlias) {
                daysValid = ssl.daysValid
                password = ssl.privateKeyPassword
                domains = ssl.domains
            }
        }.saveToFile(file, ssl.keyStorePassword)
    }

    private fun Any.log(prefix: String = "", suffix: String = "") =
        println("$TAG $prefix$this$suffix")
}

/**
 * 命令行：
 * keytool -keystore app/src/main/resources/.key/keystore.jks -alias aliasName -genkeypair -keyalg RSA -keysize 4096 -validity 3 -dname 'CN=农卷风, OU=农卷风, O=农卷风, L=深圳, ST=广东, C=中国' -ext 'SAN:c=DNS:localhost,IP:127.0.0.1,IP:0.0.0.0'
 *
 * CN(Common Name名字与姓氏)
 * OU(Organization Unit组织单位名称)
 * O(Organization组织名称)
 * L(Locality城市或区域名称)
 * ST(State州或省份名称)
 * C(Country国家名称)
 *
 * 或运行如下代码
 */
data class SSL(
    val keyStore: String,
    val keyAlias: String,
    val keyStorePassword: String,
    val privateKeyPassword: String,
    val daysValid: Long = 365,
    val domains: List<String> = listOf("localhost")
)