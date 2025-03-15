package com.example.android_mobile_banking_widgets.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.example.android_mobile_banking_widgets.R

class ExchangeRateWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId)
        }
    }

    companion object {
        fun updateWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val views = RemoteViews(context.packageName, R.layout.widget_exchange_rate)

            views.setTextViewText(R.id.tv_updated_time, "Updated: Jan 12, 2025")
            views.setTextViewText(R.id.tv_currency,"USD")

            // Fetch exchange rate from API
            val buyRate = "4,108.00 KHR"
            val sellRate = "4,120.00 KHR"

            views.setTextViewText(R.id.tv_buy, "BUY:")
            views.setTextViewText(R.id.tv_buy_value, buyRate)
            views.setTextViewText(R.id.tv_sell, "SELL:")
            views.setTextViewText(R.id.tv_sell_value, sellRate)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}