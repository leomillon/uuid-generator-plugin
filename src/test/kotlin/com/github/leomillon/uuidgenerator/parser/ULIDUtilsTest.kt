package com.github.leomillon.uuidgenerator.parser

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class ULIDUtilsTest {

    @ParameterizedTest
    @MethodSource("ulidTextProvider")
    fun `should find valid ULIDs in text`(input: String, expectedMatches: List<Pair<String, IntRange>>) {
        assertThat(input.findULIDs().toList())
            .isEqualTo(expectedMatches)
    }

    @Suppress("unused")
    private fun ulidTextProvider() = listOf(
        Arguments.of(
            "01EGWMN28E96K0NT9SPK7GZN61",
            listOf("01EGWMN28E96K0NT9SPK7GZN61" to 0..25)
        ),
        Arguments.of(
            " 01EGWMP1DXCX5NFA2ATXB53SQ5 ",
            listOf("01EGWMP1DXCX5NFA2ATXB53SQ5" to 1..26)
        ),
        Arguments.of(
            "01EGWMP1DXCX5NFA2ATXB53SQ5 01egwmpk5525ejnr1thp9vm40v",
            listOf(
                "01EGWMP1DXCX5NFA2ATXB53SQ5" to 0..25,
                "01egwmpk5525ejnr1thp9vm40v" to 27..52
            )
        ),
        // ulid (26 chars) + 1 char
        Arguments.of(
            "01EGR7BAYWS8CD94N4VNAQ9KCF1",
            emptyList<Pair<String, IntRange>>()
        ),
        // uuid with invalid 'L' char
        Arguments.of(
            "01EGWMN28E96K0NT9SPK7GZL61",
            emptyList<Pair<String, IntRange>>()
        ),
        // weird method name of 26 ULID chars with '(' just after
        Arguments.of(
            "01egwmpk5525ejnr1thp9vm40v(",
            emptyList<Pair<String, IntRange>>()
        ),
        Arguments.of(
            "FoooooooooooooBaaaaaaaaaaa fooooooooooooobaaaaaaaaaaa FOOOOOOOOOOOOOBAAAAAAAAAAA",
            emptyList<Pair<String, IntRange>>()
        )
    )
}
