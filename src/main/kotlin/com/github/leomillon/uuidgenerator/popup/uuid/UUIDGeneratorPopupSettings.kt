package com.github.leomillon.uuidgenerator.popup.uuid

import com.github.leomillon.uuidgenerator.settings.uuid.UUIDFormatSettings
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service
import com.intellij.util.xmlb.XmlSerializerUtil
import org.jetbrains.annotations.Nullable

@State(name = "UUIDGeneratorPopupSettings", storages = [(Storage("uuid_popup.xml"))])
class UUIDGeneratorPopupSettings : PersistentStateComponent<UUIDGeneratorPopupSettings>,
    UUIDFormatSettings {

    companion object {
        val instance: UUIDGeneratorPopupSettings
            get() = service()
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
