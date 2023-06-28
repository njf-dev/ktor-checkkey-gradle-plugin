# 自动生成 ktor 项目的 ssl 证书插件

[![](https://jitpack.io/v/com.njf2016/ktor-checkkey-gradle-plugin.svg)](https://jitpack.io/#com.njf2016/ktor-checkkey-gradle-plugin)

根据 ktor 项目中 conf 文件里 ssl 配置生成证书

## 引入

引入请参考 [JitPack](https://jitpack.io/#com.njf2016/ktor-checkkey-gradle-plugin)

## 使用

- 在 [build.gradle.kts](build.gradle.kts) 添加依赖

```kotlin
buildscript {
    dependencies {
        classpath("com.njf2016:ktor-checkkey-gradle-plugin:<plugin-version>")
    }
}
```

- 使用插件

```kotlin
plugins {
    // ...
    id("com.njf2016.plugin.ktor-checkkey")
}
```
