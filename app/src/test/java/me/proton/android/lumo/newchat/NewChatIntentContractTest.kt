package me.proton.android.lumo.newchat

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class NewChatIntentContractTest {

    @Test
    fun `parse returns null for unsupported action`() {
        val result = NewChatIntentContract.parse(
            action = "android.intent.action.VIEW",
            prompt = "hello",
            sharedText = null
        )

        assertNull(result)
    }

    @Test
    fun `parse prefers explicit prompt over shared text`() {
        val result = NewChatIntentContract.parse(
            action = NewChatIntentContract.ACTION_NEW_CHAT,
            prompt = "direct",
            sharedText = "fallback"
        )

        assertEquals("direct", result?.prompt)
    }

    @Test
    fun `parse trims and drops empty prompt`() {
        val result = NewChatIntentContract.parse(
            action = NewChatIntentContract.ACTION_NEW_CHAT,
            prompt = "   ",
            sharedText = null
        )

        assertNull(result?.prompt)
    }

    @Test
    fun `sanitize prompt enforces max length`() {
        val rawPrompt = "a".repeat(2_001)

        val sanitized = NewChatIntentContract.sanitizePrompt(rawPrompt)

        assertEquals(2_000, sanitized?.length)
    }
}
