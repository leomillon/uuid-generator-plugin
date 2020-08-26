package com.github.leomillon.uuidgenerator.parser

import com.github.f4b6a3.ulid.util.UlidUtil
import com.github.f4b6a3.ulid.util.UlidValidator
import com.github.leomillon.uuidgenerator.InvalidFormatException
import java.time.Instant

const val CROCKFORD_BASE32_CHARS = "0123456789ABCDEFGHJKMNPQRSTVWXYZ"
const val CROCKFORD_BASE32_CHARS_LOWER = "${CROCKFORD_BASE32_CHARS}abcdefghjkmnpqrstvwxyz"

val ULID_STRING_REGEX =
    "[${CROCKFORD_BASE32_CHARS_LOWER}]{10}[${CROCKFORD_BASE32_CHARS_LOWER}]{16}".toRegex()

val ULID_GUID_REGEX =
    "([$CROCKFORD_BASE32_CHARS_LOWER]{8})-([$CROCKFORD_BASE32_CHARS_LOWER]{4})-([0-9a-zA-Z]{4})-([0-9a-zA-Z]{4})-([0-9a-zA-Z]{12})".toRegex()

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

fun CharSequence.findULIDs() = (ULID_STRING_REGEX.findAll(this) + ULID_GUID_REGEX.findAll(this))
    .filter { ULIDParser(it.value).isValid() }
