package com.github.leomillon.uuidgenerator

import com.github.leomillon.uuidgenerator.DisplayMessageUtils.Companion.displayMessage
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ide.CopyPasteManager
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
                displayMessage("$generatedUuid copied to clipboard.", this, fadeoutTime = 2000)
            }
    }

    private fun toClipboard(text: String) {
        CopyPasteManager.getInstance().setContents(TextTransferable(text))
    }
}
