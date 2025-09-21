package com.example.android_mobile_banking_widgets.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import com.example.android_mobile_banking_widgets.R
import com.example.android_mobile_banking_widgets.activities.MainActivity

class AccountWidgetProvider : AppWidgetProvider() {
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == ACTION_REFRESH) {
            handleRefreshAction(context)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        appWidgetIds.forEach { appWidgetId ->
            updateWidget(context, appWidgetManager, appWidgetId)
        }
    }

    companion object {
        private const val ACTION_REFRESH =
            "com.example.android_mobile_banking_widgets.REFRESH"

        private fun immutableFlags(base: Int = PendingIntent.FLAG_UPDATE_CURRENT): Int {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                base or PendingIntent.FLAG_IMMUTABLE
            } else {
                base
            }
        }

        fun updateWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val views = RemoteViews(context.packageName, R.layout.widget_account_info)

            // Refresh button → broadcast
            val refreshIntent = Intent(context, AccountWidgetProvider::class.java).apply {
                action = ACTION_REFRESH
            }
            val refreshPendingIntent = PendingIntent.getBroadcast(
                context,
                /* requestCode */ 10_000 + appWidgetId, // unique per widget
                refreshIntent,
                immutableFlags()
            )
            views.setOnClickPendingIntent(R.id.btn_refresh, refreshPendingIntent)

            setUpQRClickListener(context, views, appWidgetId)
            setUpScanQRClickListener(context, views, appWidgetId)

            // TODO replace with real API data
            views.setTextViewText(R.id.tv_updated_at, "Updated: 12:06 AM | Mar 09, 2025")
            views.setImageViewResource(R.id.iv_qr_account, R.drawable.qr_code)
            views.setTextViewText(R.id.tv_account_title, "Savings")
            views.setTextViewText(R.id.tv_currency, "$")
            views.setTextViewText(R.id.tv_balance, "168.00")
            views.setTextViewText(R.id.tv_scan_qr, "Scan QR")
            views.setTextViewText(R.id.tv_share_link, "Share Link")

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        private fun handleRefreshAction(context: Context) {
            // TODO fetch real-time data
            val updatedBalance = "175.00"
            val updatedTime = "Updated: 01:30 AM | Mar 09, 2025"

            val widgetManager = AppWidgetManager.getInstance(context)
            val componentName = ComponentName(context, AccountWidgetProvider::class.java)
            val appWidgetIds = widgetManager.getAppWidgetIds(componentName)

            appWidgetIds.forEach { appWidgetId ->
                val views = RemoteViews(context.packageName, R.layout.widget_account_info)
                views.setTextViewText(R.id.tv_updated_at, updatedTime)
                views.setTextViewText(R.id.tv_balance, updatedBalance)
                widgetManager.updateAppWidget(appWidgetId, views)
            }
        }

        private fun setUpQRClickListener(context: Context, views: RemoteViews, appWidgetId: Int) {
            val qrIntent = Intent(context, MainActivity::class.java).apply {
                action = "com.example.android_mobile_banking_widgets.NAVIGATE_QR"
                putExtra("destination", "QRActivity")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            val qrPendingIntent = PendingIntent.getActivity(
                context,
                /* requestCode */ 20_000 + appWidgetId,
                qrIntent,
                immutableFlags()
            )
            views.setOnClickPendingIntent(R.id.iv_qr_account, qrPendingIntent)
        }

        private fun setUpScanQRClickListener(context: Context, views: RemoteViews, appWidgetId: Int) {
            val scanQrIntent = Intent(context, MainActivity::class.java).apply {
                action = "com.example.android_mobile_banking_widgets.NAVIGATE_SCAN_QR"
                putExtra("destination", "ScanQRActivity")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            val scanQrPendingIntent = PendingIntent.getActivity(
                context,
                /* requestCode */ 30_000 + appWidgetId,
                scanQrIntent,
                immutableFlags()
            )
            views.setOnClickPendingIntent(R.id.button_scan_qr, scanQrPendingIntent)
        }
    }
}
