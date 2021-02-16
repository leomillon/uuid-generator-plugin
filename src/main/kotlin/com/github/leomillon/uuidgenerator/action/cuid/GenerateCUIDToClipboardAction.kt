package com.github.leomillon.uuidgenerator.action.cuid

import com.github.leomillon.uuidgenerator.CUIDGenerator
import com.github.leomillon.uuidgenerator.action.GenerateToClipboardAction

/**
 * Action that generate a CUID into clipboard.
 *
 * @author dvdandroid
 */
class GenerateCUIDToClipboardAction : GenerateToClipboardAction() {
    override fun generateId(): String =
        CUIDGenerator.generateCUID()
}
