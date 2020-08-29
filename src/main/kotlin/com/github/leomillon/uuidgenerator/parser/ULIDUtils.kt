package com.github.leomillon.uuidgenerator.parser

import com.github.f4b6a3.ulid.util.UlidUtil
import com.github.f4b6a3.ulid.util.UlidValidator
import com.github.leomillon.uuidgenerator.InvalidFormatException
import java.time.Instant

const val CROCKFORD_BASE32_CHARS_UPPER = "0123456789ABCDEFGHJKMNPQRSTVWXYZ"
const val CROCKFORD_BASE32_CHARS_LOWER = "0123456789abcdefghjkmnpqrstvwxyz"

val FIND_ULID_REGEX =
    """(?<![0-9a-zA-Z])([${CROCKFORD_BASE32_CHARS_UPPER}]{26}|[${CROCKFORD_BASE32_CHARS_LOWER}]{26})(?!([0-9a-zA-Z]|\())""".toRegex()

class ULIDParser(
    private val source: String
) {

    fun isValid(): Boolean = UlidValidator.isValid(source)

    fun getInstant(): Instant = UlidUtil.extractInstant(source)

    @Throws(InvalidFormatException::class)
    fun assertValid() {
        if (!isValid()) {
            throw InvalidFormatException("$source is not a valid ulid format")
        }
    }
}

fun CharSequence.findULIDs() = FIND_ULID_REGEX.findAll(this)
    .filter { ULIDParser(it.value).isValid() }
    .filter { it.value.containsAtLeast2Numbers() }
    .map { it.value to it.range }
