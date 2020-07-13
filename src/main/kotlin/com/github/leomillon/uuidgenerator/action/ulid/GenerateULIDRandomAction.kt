package com.github.leomillon.uuidgenerator.action.ulid

import com.github.leomillon.uuidgenerator.action.GenerateRandomAction
import com.github.leomillon.uuidgenerator.ULIDGenerator

/**
 * A random ULID generator action.
 *
 * @author Léo Millon
 */
class GenerateULIDRandomAction : GenerateRandomAction() {
    override fun generateId(): String =
        ULIDGenerator.generateULID()
}
