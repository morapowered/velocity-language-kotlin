package com.velocitypowered.api.kt.plugin.meta

import com.velocitypowered.api.plugin.meta.PluginDependency
import java.util.Optional

operator fun PluginDependency.component1(): String = id
operator fun PluginDependency.component2(): Optional<String> = version
operator fun PluginDependency.component3(): Boolean = isOptional
