package me.proton.android.lumo.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import me.proton.android.lumo.MainActivity
import me.proton.android.lumo.R

/**
 * Home screen widget that exposes a single button to open a new chat in Lumo,
 * similar to Google's Gemini app widget.
 *
 * Tapping the widget launches [MainActivity] with an intent that asks it to
 * start a fresh conversation.
 */
class NewChatWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        val pendingIntent = newChatPendingIntent(context)
        appWidgetIds.forEach { appWidgetId ->
            val views = RemoteViews(context.packageName, R.layout.widget_new_chat).apply {
                setOnClickPendingIntent(R.id.widget_new_chat_root, pendingIntent)
            }
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    private fun newChatPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            action = MainActivity.ACTION_START_NEW_CHAT
            putExtra(MainActivity.EXTRA_START_NEW_CHAT, true)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        return PendingIntent.getActivity(
            context,
            REQUEST_CODE_NEW_CHAT,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private companion object {
        const val REQUEST_CODE_NEW_CHAT = 1001
    }
}
