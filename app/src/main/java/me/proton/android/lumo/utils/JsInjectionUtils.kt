package me.proton.android.lumo.utils

/**
 * Escapes [text] and wraps it in double quotes so it can be safely passed as a JavaScript string
 * literal to functions injected into the WebView (e.g. `insertPromptAndSubmit`).
 *
 * The returned value already includes the surrounding quotes.
 */
fun formatTextForJsInjection(text: String): String {
    val escaped = text
        .replace("\\", "\\\\") // Must replace backslash first!
        .replace("\"", "\\\"") // Escape double quotes
        .replace("'", "\\'")   // Escape single quotes (optional but safe)
        .replace("\n", "\\n")  // Escape newlines
        .replace("\r", "\\r")  // Escape carriage returns
    return "\"$escaped\""
}
