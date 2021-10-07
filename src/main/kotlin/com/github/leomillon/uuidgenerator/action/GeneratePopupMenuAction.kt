package com.github.leomillon.uuidgenerator.action

import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.popup.JBPopupFactory

abstract class GeneratePopupMenuAction : AnAction() {

    abstract fun popupTitle(): String

    abstract fun resolveActionGroup(): ActionGroup?

    override fun actionPerformed(e: AnActionEvent) {

        val actionGroup = resolveActionGroup() ?: return

        JBPopupFactory.getInstance().createActionGroupPopup(
            popupTitle(),
            actionGroup,
            e.dataContext,
            JBPopupFactory.ActionSelectionAid.ALPHA_NUMBERING,
            false
        )
            .showInBestPositionFor(e.dataContext)
    }
}
