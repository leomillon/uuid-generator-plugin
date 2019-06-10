package com.github.leomillon.uuidgenerator

import com.github.leomillon.uuidgenerator.popup.GeneratePopup
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.util.ui.TextTransferable

/**
 * Open the generator popup.
 *
 * @author Léo Millon
 */
class GeneratePopupAction : AnAction() {

    override fun actionPerformed(anActionEvent: AnActionEvent) {

        val project = anActionEvent.getData(CommonDataKeys.PROJECT)

        val generatePopup = GeneratePopup()

        if (!generatePopup.showAndGet()) return

        generatePopup.getOutput()
            ?.let { TextTransferable(it) }
            ?.run {
                CopyPasteManager.getInstance().setContents(this)
                project
                    ?.run {
                        DisplayMessageUtils.displayMessage("Copied to clipboard", this, fadeoutTime = 2000)
                    }
                generatePopup.saveSettings()
            }
    }
}
