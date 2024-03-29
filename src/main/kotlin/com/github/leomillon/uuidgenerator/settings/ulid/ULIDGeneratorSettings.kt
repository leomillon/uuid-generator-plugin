package com.github.leomillon.uuidgenerator.settings.ulid

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service
import com.intellij.util.xmlb.XmlSerializerUtil
import org.jetbrains.annotations.Nullable

@State(name = "ULIDGeneratorSettings", storages = [(Storage("ulid_generator.xml"))])
class ULIDGeneratorSettings : PersistentStateComponent<ULIDGeneratorSettings>,
    ULIDFormatSettings {

    companion object {
        val instance: ULIDGeneratorSettings
            get() = service()
    }

    var version = "Unknown"

    /**
     * Default values
     */
    var lowerCased = false
    var guidFormat = false
    var codeHighlighting = true

    @Nullable
    override fun getState() = this

    override fun loadState(state: ULIDGeneratorSettings) {
        XmlSerializerUtil.copyBean(state, this)
    }

    override fun isLowerCased() = lowerCased

    override fun isGuidFormat() = guidFormat
}
