package com.github.leomillon.uuidgenerator.action.cuid

import com.github.leomillon.uuidgenerator.action.GeneratePopupMenuAction
import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.ActionManager

class CUIDGeneratePopupMenuAction : GeneratePopupMenuAction() {
    override fun popupTitle(): String = "Generate CUID"

    override fun resolveActionGroup(): ActionGroup? = ActionManager.getInstance()
        .getAction("cuidgenerator.GenerateActionGroup") as? ActionGroup
}
