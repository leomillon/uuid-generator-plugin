package com.github.leomillon.uuidgenerator.parser

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class UUIDUtilsTest {

    @ParameterizedTest
    @MethodSource("uuidTextProvider")
    fun `should find valid UUIDs in text`(input: String, expectedMatches: List<Pair<String, IntRange>>) {
        assertThat(input.findUUIDs().toList())
            .isEqualTo(expectedMatches)
    }

    @Suppress("unused")
    private fun uuidTextProvider() = listOf(
        Arguments.of(
            "0ae90004-e87b-47ec-941f-8b3067c1208d",
            listOf("0ae90004-e87b-47ec-941f-8b3067c1208d" to 0..35)
        ),
        Arguments.of(
            " 0ae90004-e87b-47ec-941f-8b3067c1208d ",
            listOf("0ae90004-e87b-47ec-941f-8b3067c1208d" to 1..36)
        ),
        Arguments.of(
            "0ae90004-e87b-47ec-941f-8b3067c1208d d5dbfa67-da79-447a-99fd-274f0e21e353",
            listOf(
                "0ae90004-e87b-47ec-941f-8b3067c1208d" to 0..35,
                "d5dbfa67-da79-447a-99fd-274f0e21e353" to 37..72
            )
        ),
        // uuid (36 chars) + 1 char
        Arguments.of(
            "9d0c8e96-308c-4271-b5c9-33032e7d7f361",
            emptyList<Pair<String, IntRange>>()
        ),
        // uuid chars with only 1 number
        Arguments.of(
            "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1",
            emptyList<Pair<String, IntRange>>()
        ),
        // uuid chars with 2 numbers
        Arguments.of(
            "aaaaaaaa-aaa2-aaaa-aaaa-aaaaaaaaaaa1",
            listOf("aaaaaaaa-aaa2-aaaa-aaaa-aaaaaaaaaaa1" to 0..35)
        ),
        // uuid with invalid 'g' char
        Arguments.of(
            "0ae90004-e87b-47eg-941f-8b3067c1208d",
            emptyList<Pair<String, IntRange>>()
        ),
        // uuid without dashes
        Arguments.of(
            "52915d977db847b3802710837a10ba61",
            listOf("52915d977db847b3802710837a10ba61" to 0..31)
        ),
        // uuid without dashes with only 1 number
        Arguments.of(
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa1",
            emptyList<Pair<String, IntRange>>()
        ),
        // uuid without dashes with 2 numbers
        Arguments.of(
            "aaaaaaaaaaa2aaaaaaaaaaaaaaaaaaa1",
            listOf("aaaaaaaaaaa2aaaaaaaaaaaaaaaaaaa1" to 0..31)
        ),
        // 32 chars texts
        Arguments.of(
            "FoooooooooooooooooooBaaaaaaaaaaa fooooooooooooooooooobaaaaaaaaaaa FOOOOOOOOOOOOOOOOOOOBAAAAAAAAAAA",
            emptyList<Pair<String, IntRange>>()
        ),
        // 32 chars camelCased class name
        Arguments.of(
            "class Aaaaaaaaaaaaaaaaaaa1Bbbbbbbbbbb2",
            emptyList<Pair<String, IntRange>>()
        ),
        // 32 lower chars class name with 1 number
        Arguments.of(
            "class aaaaaaaaaaaaaaaaaaaabbbbbbbbbbb1",
            emptyList<Pair<String, IntRange>>()
        ),
        // 32 lower chars class name with 2 numbers
        Arguments.of(
            "class aaaaaaaaaaaaaaaaaaa1bbbbbbbbbbb2",
            listOf("aaaaaaaaaaaaaaaaaaa1bbbbbbbbbbb2" to 6..37)
        ),
        // 32 UPPER chars class name with 1 number
        Arguments.of(
            "class AAAAAAAAAAAAAAAAAAAABBBBBBBBBBB1",
            emptyList<Pair<String, IntRange>>()
        ),
        // 32 UPPER chars class name with 2 numbers
        Arguments.of(
            "class AAAAAAAAAAAAAAAAAAA1BBBBBBBBBBB2",
            listOf("AAAAAAAAAAAAAAAAAAA1BBBBBBBBBBB2" to 6..37)
        ),
        // 32 lower chars method name with 1 number
        Arguments.of(
            "aaaaaaaaaaaaaaaaaaaabbbbbbbbbbb1()",
            emptyList<Pair<String, IntRange>>()
        ),
        // 32 lower chars method name with 2 numbers
        Arguments.of(
            "aaaaaaaaaaaaaaaaaaa1bbbbbbbbbbb2()",
            emptyList<Pair<String, IntRange>>()
        ),
        // 32 UPPER chars class name with 1 number
        Arguments.of(
            "AAAAAAAAAAAAAAAAAAAABBBBBBBBBBB1()",
            emptyList<Pair<String, IntRange>>()
        ),
        // 32 UPPER chars class name with 2 numbers
        Arguments.of(
            "AAAAAAAAAAAAAAAAAAA1BBBBBBBBBBB2()",
            emptyList<Pair<String, IntRange>>()
        )
    )
}
