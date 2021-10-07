package com.github.leomillon.uuidgenerator.popup.ulid

import com.github.leomillon.uuidgenerator.settings.ulid.ULIDFormatSettings
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service
import com.intellij.util.xmlb.XmlSerializerUtil
import org.jetbrains.annotations.Nullable

@State(name = "ULIDGeneratorPopupSettings", storages = [(Storage("ulid_popup.xml"))])
class ULIDGeneratorPopupSettings : PersistentStateComponent<ULIDGeneratorPopupSettings>,
    ULIDFormatSettings {

    companion object {
        val instance: ULIDGeneratorPopupSettings
            get() = service()
    }

    /**
     * Default values
     */
    var lowerCased = false
    var guidFormat = false
    var numberFieldValue = 1
    var separatorFieldValue = "\\n"
    var prefixFieldValue = ""
    var suffixFieldValue = ""

    @Nullable
    override fun getState() = this

    override fun loadState(state: ULIDGeneratorPopupSettings) {
        XmlSerializerUtil.copyBean(state, this)
    }

    override fun isLowerCased() = lowerCased

    override fun isGuidFormat() = guidFormat
}
