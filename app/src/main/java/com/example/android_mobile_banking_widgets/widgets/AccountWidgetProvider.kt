package com.example.android_mobile_banking_widgets.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.android_mobile_banking_widgets.R
import com.example.android_mobile_banking_widgets.R.layout.widget_account_info

class AccountWidgetProvider : AppWidgetProvider() {
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        // Check if the received action is the one defined for refreshing the widget
        if (intent.action == "com.example.android_mobile_banking_widgets.REFRESH") {
            // Call method to refresh the widget data
            handleRefreshAction(context)
        }
    }

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        appWidgetIds?.forEach { appWidgetId ->
            updateWidget(context!!, appWidgetManager!!, appWidgetId)
        }
    }

    companion object {
        fun updateWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val views = RemoteViews(context.packageName, widget_account_info)

            // Set up the refresh button action (this sends the refresh broadcast)
            val refreshIntent = Intent(context, AccountWidgetProvider::class.java).apply {
                action = "com.example.android_mobile_banking_widgets.REFRESH"
            }
            val refreshPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                refreshIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            views.setOnClickPendingIntent(R.id.btn_refresh, refreshPendingIntent)

            // Fetch real data from API
            views.setTextViewText(R.id.tv_updated_at, "Updated: 12:06 AM | Mar 09, 2025")

            views.setImageViewResource(R.id.iv_qr_account, R.drawable.qr_code)

            views.setTextViewText(R.id.tv_account_title, "Savings")
            views.setTextViewText(R.id.tv_currency, "$")
            views.setTextViewText(R.id.tv_balance, "168.00")

            views.setTextViewText(R.id.tv_scan_qr, "Scan QR")
            views.setTextViewText(R.id.tv_share_link, "Share Link")

            // Update widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        fun handleRefreshAction(context: Context) {
            // Fetch real-time data from API
            val updatedBalance = "175.00" // Replace with actual API data
            val updatedTime = "Updated: 01:30 AM | Mar 09, 2025"

            // Update widget view with new data
            val widgetManager = AppWidgetManager.getInstance(context)
            val componentName = ComponentName(context, AccountWidgetProvider::class.java)
            val appWidgetIds = widgetManager.getAppWidgetIds(componentName)
            appWidgetIds.forEach { appWidgetId ->
                val views = RemoteViews(context.packageName, widget_account_info)
                views.setTextViewText(R.id.tv_updated_at, updatedTime)
                views.setTextViewText(R.id.tv_balance, updatedBalance)

                // Update the widget
                widgetManager.updateAppWidget(appWidgetId, views)
            }
        }
    }
}
