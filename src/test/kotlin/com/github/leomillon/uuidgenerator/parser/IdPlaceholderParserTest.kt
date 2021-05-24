package com.github.leomillon.uuidgenerator.parser

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

class IdPlaceholderParserTest {

    @TestFactory
    fun `test placeholders in text`() = idPlaceholdersTextProvider()
        .map { (input: String, expectedMatches: List<Pair<IdPlaceholder, IntRange>>) ->
            dynamicTest("should find id placeholders in text") {
                assertThat(input.findIdPlaceholders().toMap()).isEqualTo(expectedMatches.toMap())
            }
        }

    private fun idPlaceholdersTextProvider() = listOf(
        "#gen.uuid#" to listOf(
            IdPlaceholder(
                rawValue = "#gen.uuid#",
                idType = IdType.UUID,
                label = null
            ) to 0..9
        ),
        "#gen.ulid#" to listOf(
            IdPlaceholder(
                rawValue = "#gen.ulid#",
                idType = IdType.ULID,
                label = null
            ) to 0..9
        ),
        "#gen.cuid#" to listOf(
            IdPlaceholder(
                rawValue = "#gen.cuid#",
                idType = IdType.CUID,
                label = null
            ) to 0..9
        ),
        "Hello you! #gen.uuid#, #gen.ulid#, #gen.cuid# Bye bye!" to listOf(
            IdPlaceholder(
                rawValue = "#gen.uuid#",
                idType = IdType.UUID,
                label = null
            ) to 11..20,
            IdPlaceholder(
                rawValue = "#gen.ulid#",
                idType = IdType.ULID,
                label = null
            ) to 23..32,
            IdPlaceholder(
                rawValue = "#gen.cuid#",
                idType = IdType.CUID,
                label = null
            ) to 35..44
        ),
        "#gen.uuid.label_1-bis#" to listOf(
            IdPlaceholder(
                rawValue = "#gen.uuid.label_1-bis#",
                idType = IdType.UUID,
                label = "label_1-bis"
            ) to 0..21
        ),
        "#gen.ulid.label_1-bis#" to listOf(
            IdPlaceholder(
                rawValue = "#gen.ulid.label_1-bis#",
                idType = IdType.ULID,
                label = "label_1-bis"
            ) to 0..21
        ),
        "#gen.cuid.label_1-bis#" to listOf(
            IdPlaceholder(
                rawValue = "#gen.cuid.label_1-bis#",
                idType = IdType.CUID,
                label = "label_1-bis"
            ) to 0..21
        ),
        "#gen.#" to emptyList(),
        "#gen.unknown#" to emptyList(),
        "#gen.uuid.invalid label#" to emptyList(),
        "#gen.uuid.invalid.label#" to emptyList(),
        "#gen.ulid.invalid label#" to emptyList(),
        "#gen.ulid.invalid.label#" to emptyList(),
        "#gen.cuid.invalid label#" to emptyList(),
        "#gen.cuid.invalid.label#" to emptyList()
    )
}
