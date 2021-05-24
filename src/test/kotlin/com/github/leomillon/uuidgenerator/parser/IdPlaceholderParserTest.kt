package com.github.leomillon.uuidgenerator.parser

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class IdPlaceholderParserTest {

    @ParameterizedTest
    @MethodSource("idPlaceholdersTextProvider")
    fun `should find id placeholders in text`(input: String, expectedMatches: List<Pair<IdPlaceholder, IntRange>>) {
        assertThat(input.findIdPlaceholders().toMap())
            .isEqualTo(expectedMatches.toMap())
    }

    @Suppress("unused")
    private fun idPlaceholdersTextProvider() = listOf(
        Arguments.of(
            "#gen.uuid#",
            listOf(
                IdPlaceholder(
                    rawValue = "#gen.uuid#",
                    idType = IdType.UUID,
                    label = null
                ) to 0..9
            )
        ),
        Arguments.of(
            "#gen.ulid#",
            listOf(
                IdPlaceholder(
                    rawValue = "#gen.ulid#",
                    idType = IdType.ULID,
                    label = null
                ) to 0..9
            )
        ),
        Arguments.of(
            "#gen.cuid#",
            listOf(
                IdPlaceholder(
                    rawValue = "#gen.cuid#",
                    idType = IdType.CUID,
                    label = null
                ) to 0..9
            )
        ),
        Arguments.of(
            "Hello you! #gen.uuid#, #gen.ulid#, #gen.cuid# Bye bye!",
            listOf(
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
            )
        ),
        Arguments.of(
            "#gen.uuid.label_1-bis#",
            listOf(
                IdPlaceholder(
                    rawValue = "#gen.uuid.label_1-bis#",
                    idType = IdType.UUID,
                    label = "label_1-bis"
                ) to 0..21
            )
        ),
        Arguments.of(
            "#gen.ulid.label_1-bis#",
            listOf(
                IdPlaceholder(
                    rawValue = "#gen.ulid.label_1-bis#",
                    idType = IdType.ULID,
                    label = "label_1-bis"
                ) to 0..21
            )
        ),
        Arguments.of(
            "#gen.cuid.label_1-bis#",
            listOf(
                IdPlaceholder(
                    rawValue = "#gen.cuid.label_1-bis#",
                    idType = IdType.CUID,
                    label = "label_1-bis"
                ) to 0..21
            )
        ),
        Arguments.of(
            "#gen.#",
            emptyList<Pair<IdPlaceholder, IntRange>>()
        ),
        Arguments.of(
            "#gen.unknown#",
            emptyList<Pair<IdPlaceholder, IntRange>>()
        ),
        Arguments.of(
            "#gen.uuid.invalid label#",
            emptyList<Pair<IdPlaceholder, IntRange>>()
        ),
        Arguments.of(
            "#gen.uuid.invalid.label#",
            emptyList<Pair<IdPlaceholder, IntRange>>()
        ),
        Arguments.of(
            "#gen.ulid.invalid label#",
            emptyList<Pair<IdPlaceholder, IntRange>>()
        ),
        Arguments.of(
            "#gen.ulid.invalid.label#",
            emptyList<Pair<IdPlaceholder, IntRange>>()
        ),
        Arguments.of(
            "#gen.cuid.invalid label#",
            emptyList<Pair<IdPlaceholder, IntRange>>()
        ),
        Arguments.of(
            "#gen.cuid.invalid.label#",
            emptyList<Pair<IdPlaceholder, IntRange>>()
        )
    )
}
