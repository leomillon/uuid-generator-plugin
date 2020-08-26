package com.github.leomillon.uuidgenerator.action

import com.github.leomillon.uuidgenerator.DisplayMessageUtils.Companion.displayMessage
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.util.ui.TextTransferable

/**
 * Action that generate an ID into clipboard.
 *
 * @author Léo Millon
 */
abstract class GenerateToClipboardAction : AnAction() {

    override fun actionPerformed(anActionEvent: AnActionEvent) {

        val generatedId = generateId()

        toClipboard(generatedId)

        anActionEvent.getData(CommonDataKeys.PROJECT)
            ?.run {
                displayMessage("$generatedId copied to clipboard.", this, fadeoutTime = 2000)
            }
    }

    private fun toClipboard(text: CharSequence) {
        CopyPasteManager.getInstance().setContents(TextTransferable(text))
    }

    abstract fun generateId(): String
}
