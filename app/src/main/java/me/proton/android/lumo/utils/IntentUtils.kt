package me.proton.android.lumo.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.core.net.toUri

/**
 * Explicit extra used to pass a prompt to [me.proton.android.lumo.MainActivity] from internal
 * launches (e.g. an assistant overlay) so that it starts a new chat with the given text.
 */
const val EXTRA_PROMPT = "me.proton.android.lumo.extra.PROMPT"

/**
 * Explicit extra used to indicate whether the prompt carried by [EXTRA_PROMPT] should be
 * submitted automatically (rather than only inserted into the composer) once injected.
 */
const val EXTRA_SUBMIT = "me.proton.android.lumo.extra.SUBMIT"

/**
 * Whether the prompt carried by an incoming [Intent] should be submitted automatically once
 * injected into the chat. Defaults to `false`, i.e. the prompt is only inserted into the composer.
 */
fun Intent.extractShouldSubmit(): Boolean = getBooleanExtra(EXTRA_SUBMIT, false)

/**
 * Extracts the prompt text carried by an incoming [Intent], if any.
 *
 * Supports:
 * - [Intent.ACTION_SEND] with `text/plain` (text shared from another app),
 * - [Intent.ACTION_PROCESS_TEXT] (text selected in another app),
 * - an internal launch carrying [EXTRA_PROMPT].
 *
 * Returns `null` when the intent carries no usable text (e.g. the launcher intent).
 */
fun Intent.extractPromptText(): String? {
    val raw = when (action) {
        Intent.ACTION_SEND -> getStringExtra(Intent.EXTRA_TEXT)
        Intent.ACTION_PROCESS_TEXT -> getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)?.toString()
        else -> getStringExtra(EXTRA_PROMPT)
    }
    return raw?.trim()?.ifBlank { null }
}

fun openSettingsIntent(packageName: String): Intent =
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

fun Activity.openSettings() {
    startActivity(openSettingsIntent(packageName))
}

fun Activity.openExternalUrl(url: String) {
    startActivity(
        Intent(
            Intent.ACTION_VIEW,
            url.toUri()
        )
    )
}
