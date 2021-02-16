package com.github.leomillon.uuidgenerator.popup.cuid

import com.github.leomillon.uuidgenerator.popup.GeneratePopup
import javax.swing.JComponent

class GenerateCUIDPopup : GeneratePopup() {

    var popupForm: CUIDGeneratorPopupForm? = null

    init {
        init()
        title = "CUID generator Popup"
    }

    override fun createCenterPanel(): JComponent? {
        popupForm = popupForm ?: CUIDGeneratorPopupForm()
        return popupForm?.component()
    }

    override fun getOutput() = popupForm?.getOutput()

    override fun saveSettings() {
        popupForm?.applyToSettings(CUIDGeneratorPopupSettings.instance)
    }
}
