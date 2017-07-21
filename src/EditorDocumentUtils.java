import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import java.util.UUID;

public final class EditorDocumentUtils {

  public static void insertTextAtCaret(final Caret caret, final CharSequence text) {
    int textLength = text.length();
    int start;
    Document document = caret.getEditor().getDocument();
    if (caret.hasSelection()) {
      start = caret.getSelectionStart();
      int end = caret.getSelectionEnd();

      document.replaceString(start, end, text);
      caret.setSelection(start, start + textLength);
    } else {
      start = caret.getOffset();

      document.insertString(start, text);
    }
    caret.moveToOffset(start + textLength);
  }

  public static String toggleUUIDDashes(final String text) {
    if (text.contains("-")) {
      return EditorDocumentUtils.removeDashes(text);
    }
    return insertDashes(text);
  }

  private static String removeDashes(final String text) {
    return text.replaceAll("-", "");
  }

  private static String insertDashes(final String text) {

    if (text.length() != 32) {
      throw new InvalidFormatException(text + " is not a valid uuid without dashes");
    }

    String result = text.substring(0, 8) +
        "-" +
        text.substring(8, 12) +
        "-" +
        text.substring(12, 16) +
        "-" +
        text.substring(16, 20) +
        "-" +
        text.substring(20);

    try {
      return UUID.fromString(result).toString();
    }
    catch (IllegalArgumentException e) {
      throw new InvalidFormatException(text + " is not a valid uuid without dashes", e);
    }
  }
}
