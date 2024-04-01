package com.njf2016.plugin

import org.gradle.api.Project
import org.gradle.api.provider.Property

/**
 * @author <a href=mailto:mcxinyu@foxmail.com>yuefeng</a> in 2024/4/1.
 */

inline fun <reified T> Project.property(defaultValue: T?): Property<T> =
    objects.property(T::class.java).convention(defaultValue)
