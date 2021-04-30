package com.github.leomillon.uuidgenerator.annotator

import assertk.assertThat
import assertk.assertions.containsOnly
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEmpty
import com.github.leomillon.uuidgenerator.parser.textRange
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.impl.source.tree.injected.changesHandler.range
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.intellij.lang.annotations.Language

class IdPlaceholderAnnotatorTest : BasePlatformTestCase() {

    fun `test should annotate id placeholders in code`() {

        // Given
        @Language("Java")
        val code = """
        class SomeJava {
            public static void main(String[] args) {
            
                // ID placeholders
                System.out.println("Here is some UUID placeholder : #gen.uuid#");
                System.out.println("Here is some ULID placeholder : #gen.ulid#");
                System.out.println("Here is some CUID placeholder : #gen.cuid#");
                
                // Labeled placeholders
                System.out.println("#gen.uuid.label_1#");
                System.out.println("#gen.ulid.label_1#");
                System.out.println("#gen.cuid.label_1#");
                
                // Some invalid placeholders
                System.out.println("#gen.unknown#");
                System.out.println("#gen.uuid.invalid label#");
                System.out.println("#gen.uuid.invalid.label#");
            }
        }
        """.trimIndent()
        myFixture.configureByText(JavaFileType.INSTANCE, code)
        PsiDocumentManager.getInstance(project).commitAllDocuments()

        // When
        val highlightingResult = myFixture.doHighlighting()

        // Then
        assertThat(highlightingResult).isNotEmpty()

        listOf(
            (154..163) to "Random UUID placeholder",
            (228..237) to "Random ULID placeholder",
            (302..311) to "Random CUID placeholder",
            (385..402) to "Random UUID placeholder labeled 'label_1'",
            (435..452) to "Random ULID placeholder labeled 'label_1'",
            (485..502) to "Random CUID placeholder labeled 'label_1'"
        )
            .forEach { (range, description) ->
                assertThat(highlightingResult.find { it.range == range.textRange() }?.description).isEqualTo(description)
            }

        val placeholdersHighlights = highlightingResult
            .filter { it.description?.startsWith("Random ") ?: false }
            .toList()
        placeholdersHighlights
            .forEach { result ->
                assertThat(result.quickFixActionRanges.map { it.first.action.text }.toList())
                    .containsOnly("Replace with new random value")
            }
        assertThat(placeholdersHighlights.count()).isEqualTo(6)
    }
}
