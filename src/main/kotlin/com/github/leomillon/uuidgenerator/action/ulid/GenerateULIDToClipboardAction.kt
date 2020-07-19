package com.github.leomillon.uuidgenerator.action.ulid

import com.github.leomillon.uuidgenerator.action.GenerateToClipboardAction
import com.github.leomillon.uuidgenerator.ULIDGenerator

/**
 * Action that generate a ULID into clipboard.
 *
 * @author Léo Millon
 */
class GenerateULIDToClipboardAction : GenerateToClipboardAction() {
    override fun generateId(): String =
        ULIDGenerator.generateULID()
}
