package com.github.leomillon.uuidgenerator.parser

const val CUID_LENGTH = 25

val FIND_CUID_REGEX = """[cC][a-zA-Z0-9]{24}""".toRegex()

class CUIDParser(
    private val source: String
) {

    fun isValid(): Boolean = source.length == CUID_LENGTH && FIND_CUID_REGEX.matchEntire(source) != null
}

fun CharSequence.findCUIDs() = FIND_CUID_REGEX.findAll(this)
    .filter { CUIDParser(it.value).isValid() }
    .map { it.value to it.range }
