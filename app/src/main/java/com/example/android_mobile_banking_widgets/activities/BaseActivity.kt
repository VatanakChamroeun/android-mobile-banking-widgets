package com.example.android_mobile_banking_widgets.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android_mobile_banking_widgets.R

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Any common setup for all activities goes here
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
