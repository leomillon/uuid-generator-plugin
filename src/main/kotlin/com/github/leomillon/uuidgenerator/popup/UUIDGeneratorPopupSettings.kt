package com.github.leomillon.uuidgenerator.popup

import com.github.leomillon.uuidgenerator.UUIDFormatSettings
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import org.jetbrains.annotations.Nullable

@State(name = "UUIDGeneratorPopupSettings", storages = [(Storage("uuid_popup.xml"))])
class UUIDGeneratorPopupSettings : PersistentStateComponent<UUIDGeneratorPopupSettings>, UUIDFormatSettings {

    companion object {
        val instance: UUIDGeneratorPopupSettings
            get() = ServiceManager.getService(UUIDGeneratorPopupSettings::class.java)
    }

    /**
     * Default values
     */
    var lowerCased = true
    var withDashes = true
    var longSize = true
    var numberFieldValue = 1
    var separatorFieldValue = "\\n"
    var prefixFieldValue = ""
    var suffixFieldValue = ""

    @Nullable
    override fun getState() = this

    override fun loadState(state: UUIDGeneratorPopupSettings) {
        XmlSerializerUtil.copyBean(state, this)
    }

    override fun isLowerCased() = lowerCased

    override fun isWithDashes() = withDashes

    override fun isLongSize() = longSize
}
