package com.github.leomillon.uuidgenerator.parser

const val CUID_LENGTH = 25

private val CUID_REGEX = """[cC][a-zA-Z0-9]{24}""".toRegex()
private val FIND_CUID_REGEX = """(?<![0-9a-zA-Z])($CUID_REGEX)(?!([0-9a-zA-Z]|\())""".toRegex()

class CUIDParser(
    private val source: String
) {

    fun isValid(): Boolean = source.length == CUID_LENGTH && CUID_REGEX.matchEntire(source) != null
}

fun CharSequence.findCUIDs() = FIND_CUID_REGEX.findAll(this)
    .filter { CUIDParser(it.value).isValid() }
    .map { it.value to it.range }
