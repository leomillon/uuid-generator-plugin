package com.github.leomillon.uuidgenerator.popup.cuid

import com.github.leomillon.uuidgenerator.settings.cuid.CUIDFormatSettings
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service
import com.intellij.util.xmlb.XmlSerializerUtil
import org.jetbrains.annotations.Nullable

@State(name = "CUIDGeneratorPopupSettings", storages = [(Storage("cuid_popup.xml"))])
class CUIDGeneratorPopupSettings : PersistentStateComponent<CUIDGeneratorPopupSettings>,
    CUIDFormatSettings {

    companion object {
        val instance: CUIDGeneratorPopupSettings
            get() = service()
    }

    /**
     * Default values
     */
    var lowerCased = true
    var numberFieldValue = 1
    var separatorFieldValue = "\\n"
    var prefixFieldValue = ""
    var suffixFieldValue = ""

    @Nullable
    override fun getState() = this

    override fun loadState(state: CUIDGeneratorPopupSettings) {
        XmlSerializerUtil.copyBean(state, this)
    }

    override fun isLowerCased() = lowerCased
}
