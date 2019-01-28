package com.github.leomillon.uuidgenerator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EditorDocumentUtilsTest {

    private static final String UUID_NO_DASH = "f228078510824c8baeffbb852eb98783";
    private static final String UUID = "f2280785-1082-4c8b-aeff-bb852eb98783";

    @Test
    void should_remove_dashes_from_uuid() {

        // When
        String result = EditorDocumentUtils.toggleUUIDDashes(UUID);

        // Then
        assertEquals(result, UUID_NO_DASH);
    }
    @Test
    void should_insert_dashes_into_uuid() {

        // When
        String result = EditorDocumentUtils.toggleUUIDDashes(UUID_NO_DASH);

        // Then
        assertEquals(result, UUID);
    }

    @Test
    void should_throw_invalid_format_exception_for_invalid_uuid() {
        assertThrows(InvalidFormatException.class, () -> {
            EditorDocumentUtils.toggleUUIDDashes("f2280785 0824c8baeffbb852eb98783");
        });
    }

}
