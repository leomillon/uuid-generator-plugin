package com.github.leomillon.uuidgenerator.action.uuid

import com.github.leomillon.uuidgenerator.action.GeneratePopupMenuAction
import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.ActionManager

class UUIDGeneratePopupMenuAction : GeneratePopupMenuAction() {
    override fun popupTitle(): String = "Generate UUID"

    override fun resolveActionGroup(): ActionGroup? = ActionManager.getInstance()
        .getAction("uuidgenerator.GenerateActionGroup") as? ActionGroup
}
