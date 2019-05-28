package com.github.leomillon.uuidgenerator

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.MessageType
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.wm.WindowManager
import com.intellij.ui.awt.RelativePoint
import com.intellij.util.ui.TextTransferable
import java.util.*

/**
 * Action that generate a UUID into clipboard.
 *
 * @author Léo Millon
 */
class GenerateToClipboardAction : AnAction() {

    override fun actionPerformed(anActionEvent: AnActionEvent) {

        val generatedUuid = UUID.randomUUID()

        toClipboard(generatedUuid.toString())

        anActionEvent.getData(CommonDataKeys.PROJECT)
            ?.run {
                displayMessage("$generatedUuid copied to clipboard.", this)
            }
    }

    private fun toClipboard(text: String) {
        CopyPasteManager.getInstance().setContents(TextTransferable(text))
    }

    private fun displayMessage(message: String, project: Project) {
        val statusBar = WindowManager.getInstance().getStatusBar(project)
        JBPopupFactory.getInstance()
            .createHtmlTextBalloonBuilder(message, MessageType.INFO, null)
            .setFadeoutTime(2000)
            .createBalloon()
            .show(RelativePoint.getCenterOf(statusBar.component), Balloon.Position.atRight)
    }
}
