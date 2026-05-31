package me.proton.android.lumo.utils

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class JsInjectionUtilsTest {

    @Test
    fun `wraps plain text in double quotes`() {
        assertThat(formatTextForJsInjection("hello")).isEqualTo("\"hello\"")
    }

    @Test
    fun `escapes backslashes before other characters`() {
        assertThat(formatTextForJsInjection("a\\b")).isEqualTo("\"a\\\\b\"")
    }

    @Test
    fun `escapes double quotes`() {
        assertThat(formatTextForJsInjection("say \"hi\"")).isEqualTo("\"say \\\"hi\\\"\"")
    }

    @Test
    fun `escapes single quotes`() {
        assertThat(formatTextForJsInjection("it's")).isEqualTo("\"it\\'s\"")
    }

    @Test
    fun `escapes newlines and carriage returns`() {
        assertThat(formatTextForJsInjection("line1\r\nline2"))
            .isEqualTo("\"line1\\r\\nline2\"")
    }

    @Test
    fun `handles empty text`() {
        assertThat(formatTextForJsInjection("")).isEqualTo("\"\"")
    }
}
