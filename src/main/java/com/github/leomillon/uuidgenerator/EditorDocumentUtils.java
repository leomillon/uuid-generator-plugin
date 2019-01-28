/*
 * The MIT License (MIT)
 *
 *  Copyright (c) 2019 Léo Millon
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package com.github.leomillon.uuidgenerator;

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
