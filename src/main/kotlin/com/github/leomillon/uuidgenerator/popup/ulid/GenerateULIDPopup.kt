package com.github.leomillon.uuidgenerator.popup.ulid

import com.github.leomillon.uuidgenerator.popup.GeneratePopup
import javax.swing.JComponent

class GenerateULIDPopup : GeneratePopup() {

    var popupForm: ULIDGeneratorPopupForm? = null

    init {
        init()
        title = "ULID generator Popup"
    }

    override fun createCenterPanel(): JComponent? {
        popupForm = popupForm ?: ULIDGeneratorPopupForm()
        return popupForm?.component()
    }

    override fun getOutput() = popupForm?.getOutput()

    override fun saveSettings() {
        popupForm?.applyToSettings(ULIDGeneratorPopupSettings.instance)
    }
}
