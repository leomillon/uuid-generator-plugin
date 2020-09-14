package com.github.leomillon.uuidgenerator.parser

import com.github.leomillon.uuidgenerator.InvalidFormatException

const val UUID_WITH_DASHES_LENGTH = 36
const val UUID_WITHOUT_DASH_LENGTH = 32

private const val UUID_CHARS_LOWER = "0123456789abcdef"
private const val UUID_CHARS_UPPER = "0123456789ABCDEF"

private val UUID_LONG_LOWER_REGEX =
    """([${UUID_CHARS_LOWER}]{8})-?([${UUID_CHARS_LOWER}]{4})-?([${UUID_CHARS_LOWER}]{4})-?([${UUID_CHARS_LOWER}]{4})-?([${UUID_CHARS_LOWER}]{12})""".toRegex()
private val UUID_LONG_UPPER_REGEX =
    """([${UUID_CHARS_UPPER}]{8})-?([${UUID_CHARS_UPPER}]{4})-?([${UUID_CHARS_UPPER}]{4})-?([${UUID_CHARS_UPPER}]{4})-?([${UUID_CHARS_UPPER}]{12})""".toRegex()

private val FIND_UUID_LONG_REGEX =
    """(?<![0-9a-zA-Z])(([${UUID_CHARS_LOWER}]{8})-([${UUID_CHARS_LOWER}]{4})-([${UUID_CHARS_LOWER}]{4})-([${UUID_CHARS_LOWER}]{4})-([${UUID_CHARS_LOWER}]{12})|([${UUID_CHARS_UPPER}]{8})-([${UUID_CHARS_UPPER}]{4})-([${UUID_CHARS_UPPER}]{4})-([${UUID_CHARS_UPPER}]{4})-([${UUID_CHARS_UPPER}]{12}))(?!([0-9a-zA-Z]|\())""".toRegex()

private val UUID_SHORT_REGEX =
    "([0-9a-zA-Z]{8})".toRegex()

class UUIDParser(
    private val source: CharSequence,
    private val allowShortFormat: Boolean = true
) {

    fun isValid(): Boolean {
        if (allowShortFormat && isShort()) {
            return true
        }

        val length = source.length
        if (length != UUID_WITHOUT_DASH_LENGTH && length != UUID_WITH_DASHES_LENGTH) {
            return false
        }

        return UUID_LONG_LOWER_REGEX.matchEntire(source) != null || UUID_LONG_UPPER_REGEX.matchEntire(source) != null
    }

    fun isShort(): Boolean = UUID_SHORT_REGEX.matchEntire(source) != null

    @Throws(InvalidFormatException::class)
    fun assertValid() {
        if (!isValid()) {
            throw InvalidFormatException("$source is not a valid uuid format")
        }
    }
}

fun CharSequence.findUUIDs() = FIND_UUID_LONG_REGEX.findAll(this)
    .filter { it.value.containsAtLeast2Numbers() }
    .map { it.value to it.range }
