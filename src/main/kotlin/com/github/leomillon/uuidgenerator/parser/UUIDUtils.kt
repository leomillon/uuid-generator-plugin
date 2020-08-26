package com.github.leomillon.uuidgenerator.parser

import com.github.leomillon.uuidgenerator.InvalidFormatException
import com.intellij.openapi.util.TextRange

val UUID_LONG_REGEX =
    "([0-9a-zA-Z]{8})-?([0-9a-zA-Z]{4})-?([0-9a-zA-Z]{4})-?([0-9a-zA-Z]{4})-?([0-9a-zA-Z]{12})".toRegex()

val UUID_SHORT_REGEX =
    "([0-9a-zA-Z]{8})".toRegex()

class UUIDParser(
    private val source: CharSequence,
    private val allowShortFormat: Boolean = true
) {

    fun isValid(): Boolean {
        if (allowShortFormat && isShort()) {
            return true
        }

        return UUID_LONG_REGEX.matchEntire(source) != null
    }

    fun isShort(): Boolean = UUID_SHORT_REGEX.matchEntire(source) != null

    @Throws(InvalidFormatException::class)
    fun assertValid() {
        if (!isValid()) {
            throw InvalidFormatException("$source is not a valid uuid format")
        }
    }
}

fun CharSequence.findUUIDs() = UUID_LONG_REGEX.findAll(this)

fun MatchResult.textRange(offset: Int = 0) = this.range.let {
    TextRange(
        offset + it.first,
        offset + it.last + 1
    )
}
