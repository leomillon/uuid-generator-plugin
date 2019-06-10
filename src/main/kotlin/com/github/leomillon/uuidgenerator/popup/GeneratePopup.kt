package com.github.leomillon.uuidgenerator.popup

import com.github.leomillon.uuidgenerator.settings.UUIDGeneratorPopupForm
import com.intellij.openapi.ui.DialogWrapper
import javax.swing.Action
import javax.swing.JComponent

class GeneratePopup : DialogWrapper(true) {

    var popupForm: UUIDGeneratorPopupForm? = null

    init {
        init()
        title = "UUID generator Popup"
    }

    override fun createCenterPanel(): JComponent? {
        popupForm = popupForm ?: UUIDGeneratorPopupForm()
        return popupForm?.component()
    }

    override fun createActions(): Array<Action> {
        val actions = super.createActions()
        okAction.putValue(Action.NAME, "Copy to clipboard")
        return actions
    }

    fun getOutput() = popupForm?.getOutput()

    fun saveSettings() {
        popupForm?.applyToSettings(UUIDGeneratorPopupSettings.instance)
    }
}
