package com.github.leomillon.uuidgenerator.action.cuid

import com.github.leomillon.uuidgenerator.CUIDGenerator
import com.github.leomillon.uuidgenerator.action.GenerateRandomAction

/**
 * A random CUID generator action.
 *
 * @author dvdandroid
 */
class GenerateCUIDRandomAction : GenerateRandomAction() {
    override fun generateId(): String =
        CUIDGenerator.generateCUID()
}
