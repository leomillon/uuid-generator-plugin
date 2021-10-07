package com.github.leomillon.uuidgenerator.settings.cuid

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service
import com.intellij.util.xmlb.XmlSerializerUtil
import org.jetbrains.annotations.Nullable

@State(name = "CUIDGeneratorSettings", storages = [(Storage("cuid_generator.xml"))])
class CUIDGeneratorSettings : PersistentStateComponent<CUIDGeneratorSettings>,
    CUIDFormatSettings {

    companion object {
        val instance: CUIDGeneratorSettings
            get() = service()
    }

    var version = "Unknown"

    /**
     * Default values
     */
    var lowerCased = true
    var codeHighlighting = true

    @Nullable
    override fun getState() = this

    override fun loadState(state: CUIDGeneratorSettings) {
        XmlSerializerUtil.copyBean(state, this)
    }

    override fun isLowerCased() = lowerCased
}
