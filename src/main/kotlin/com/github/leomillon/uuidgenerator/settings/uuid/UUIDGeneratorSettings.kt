package com.github.leomillon.uuidgenerator.settings.uuid

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import org.jetbrains.annotations.Nullable

@State(name = "UUIDGeneratorSettings", storages = [(Storage("uuid_generator.xml"))])
class UUIDGeneratorSettings : PersistentStateComponent<UUIDGeneratorSettings>,
    UUIDFormatSettings {

    companion object {
        val instance: UUIDGeneratorSettings
            get() = ServiceManager.getService(UUIDGeneratorSettings::class.java)
    }

    var version = "Unknown"

    /**
     * Default values
     */
    var lowerCased = true
    var withDashes = true
    var longSize = true

    @Nullable
    override fun getState() = this

    override fun loadState(state: UUIDGeneratorSettings) {
        XmlSerializerUtil.copyBean(state, this)
    }

    override fun isLowerCased() = lowerCased

    override fun isWithDashes() = withDashes

    override fun isLongSize() = longSize
}
