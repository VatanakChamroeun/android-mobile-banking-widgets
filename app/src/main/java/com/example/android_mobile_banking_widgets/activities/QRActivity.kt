package com.example.android_mobile_banking_widgets.activities

import android.os.Bundle
import android.os.PersistableBundle
import com.example.android_mobile_banking_widgets.R

class QRActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.fragment_qr)
    }
}