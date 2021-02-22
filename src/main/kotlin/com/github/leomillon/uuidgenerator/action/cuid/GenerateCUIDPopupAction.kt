package com.github.leomillon.uuidgenerator.action.cuid

import com.github.leomillon.uuidgenerator.action.GeneratePopupAction
import com.github.leomillon.uuidgenerator.popup.GeneratePopup
import com.github.leomillon.uuidgenerator.popup.cuid.GenerateCUIDPopup

/**
 * Open the CUID generator popup.
 *
 * @author dvdandroid
 */
class GenerateCUIDPopupAction : GeneratePopupAction() {
    override fun createPopup(): GeneratePopup =
        GenerateCUIDPopup()
}
