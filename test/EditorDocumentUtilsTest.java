import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EditorDocumentUtilsTest {

  @Test
  public void should_remove_dashes_from_uuid() throws Exception {

    // Given
    String uuidText = "f2280785-1082-4c8b-aeff-bb852eb98783";

    // When
    String result = EditorDocumentUtils.toggleUUIDDashes(uuidText);

    // Then
    assertEquals(result, "f228078510824c8baeffbb852eb98783");
  }
  @Test
  public void should_insert_dashes_into_uuid() throws Exception {

    // Given
    String uuidText = "f228078510824c8baeffbb852eb98783";

    // When
    String result = EditorDocumentUtils.toggleUUIDDashes(uuidText);

    // Then
    assertEquals(result, "f2280785-1082-4c8b-aeff-bb852eb98783");
  }

  @Test(expected = InvalidFormatException.class)
  public void should_throw_invalid_format_exception_for_invalid_uuid() throws Exception {
    EditorDocumentUtils.toggleUUIDDashes("f2280785 0824c8baeffbb852eb98783");
  }
}
