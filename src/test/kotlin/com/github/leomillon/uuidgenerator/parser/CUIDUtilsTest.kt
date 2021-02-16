package com.github.leomillon.uuidgenerator.parser

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class CUIDUtilsTest {

    @ParameterizedTest
    @MethodSource("cuidTextProvider")
    fun `should find valid CUIDs in text`(input: String, expectedMatches: List<Pair<String, IntRange>>) {
        assertThat(input.findCUIDs().toList())
            .isEqualTo(expectedMatches)
    }

    @Suppress("unused")
    private fun cuidTextProvider() = listOf(
        Arguments.of(
            "ckl87yain000m01jp66q176yr",
            listOf("ckl87yain000m01jp66q176yr" to 0..24)
        ),
        Arguments.of(
            " ckl87yi32000n01jp5t325xsa ",
            listOf("ckl87yi32000n01jp5t325xsa" to 1..25)
        ),
        Arguments.of(
            "CKL87YL91000O01JP3RVF6DFV ckl87ypmn000p01jpfzu1g3bt",
            listOf(
                "CKL87YL91000O01JP3RVF6DFV" to 0..24,
                "ckl87ypmn000p01jpfzu1g3bt" to 26..50
            )
        ),
        Arguments.of(
            "FoooooooooooooBaaaaaaaaaaa fooooooooooooobaaaaaaaaaaa FOOOOOOOOOOOOOBAAAAAAAAAAA",
            emptyList<Pair<String, IntRange>>()
        )
    )
}
