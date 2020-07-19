package com.github.leomillon.uuidgenerator.popup

import com.github.leomillon.uuidgenerator.settings.UUIDGeneratorPopupForm
import com.intellij.openapi.ui.DialogWrapper
import javax.swing.Action
import javax.swing.JComponent

interface IGeneratePopup {

    fun getOutput(): String?

    fun saveSettings()
}
