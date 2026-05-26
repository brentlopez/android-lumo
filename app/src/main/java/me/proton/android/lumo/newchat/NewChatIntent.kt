package me.proton.android.lumo.newchat

import android.content.Intent

data class NewChatRequest(val prompt: String?)

object NewChatIntentContract {
    const val ACTION_NEW_CHAT = "me.proton.android.lumo.action.NEW_CHAT"
    const val EXTRA_PROMPT = "me.proton.android.lumo.extra.PROMPT"
    private const val MAX_PROMPT_LENGTH = 2_000

    fun sanitizePrompt(rawPrompt: String?): String? =
        rawPrompt
            ?.trim()
            ?.takeIf { it.isNotEmpty() }
            ?.take(MAX_PROMPT_LENGTH)

    fun parse(
        action: String?,
        prompt: String?,
        sharedText: String?,
    ): NewChatRequest? {
        if (action != ACTION_NEW_CHAT) {
            return null
        }
        return NewChatRequest(sanitizePrompt(prompt ?: sharedText))
    }
}

fun Intent?.toNewChatRequest(): NewChatRequest? {
    val intent = this ?: return null
    return NewChatIntentContract.parse(
        action = intent.action,
        prompt = intent.getStringExtra(NewChatIntentContract.EXTRA_PROMPT),
        sharedText = intent.getStringExtra(Intent.EXTRA_TEXT),
    )
}
