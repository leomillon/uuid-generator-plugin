package com.github.leomillon.uuidgenerator.action

import com.github.leomillon.uuidgenerator.DisplayMessageUtils
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
abstract class GeneratePopupAction : AnAction() {

    override fun actionPerformed(anActionEvent: AnActionEvent) {

        val project = anActionEvent.getData(CommonDataKeys.PROJECT)

        val generatePopup = createPopup()

        if (!generatePopup.showAndGet()) return

        generatePopup.getOutput()
            ?.let { TextTransferable(it as CharSequence) }
            ?.run {
                CopyPasteManager.getInstance().setContents(this)
                project
                    ?.run {
                        DisplayMessageUtils.displayMessage(
                            "Copied to clipboard",
                            this,
                            fadeoutTime = 2000
                        )
                    }
                generatePopup.saveSettings()
            }
    }

    abstract fun createPopup(): GeneratePopup
}
