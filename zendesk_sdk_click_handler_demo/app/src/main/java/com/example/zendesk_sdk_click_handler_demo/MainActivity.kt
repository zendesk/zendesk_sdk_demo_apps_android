package com.example.zendesk_sdk_click_handler_demo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import zendesk.android.Zendesk
import zendesk.android.messaging.Messaging
import zendesk.android.messaging.MessagingDelegate
import zendesk.android.messaging.UrlSource
import zendesk.logger.Logger

class MainActivity : AppCompatActivity() {

    private val LOG_TAG = "[${this.javaClass.name}]"

    private var coordinatorLayout: CoordinatorLayout? = null

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.topAppBar))
        coordinatorLayout = findViewById(R.id.coordinatorLayout)

        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/troubleshooting
        Logger.setLoggable(true)

        findViewById<Button>(R.id.InitButton).isVisible = false

        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/getting_started/#4-show-messaging
        findViewById<Button>(R.id.StartButton).setOnClickListener {
            Zendesk.instance.messaging.showMessaging(this)
        }
        addClickHandler()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.about -> {
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.about_title)
                .setMessage(R.string.about_message)
                .setPositiveButton("OK", null)
                .show()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    // SDK related methods

    // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/click_delegates
    private fun addClickHandler(){
        Messaging.setDelegate(object : MessagingDelegate() {
            override fun shouldHandleUrl(url: String, urlSource: UrlSource): Boolean {
                // Your custom action...
                Toast.makeText(applicationContext, getString(R.string.msg_click_toast), Toast.LENGTH_LONG).show()
                Log.d(LOG_TAG, getString(R.string.msg_click_toast) + " - url: $url - urlSource: $urlSource")
                // Return false to prevent the SDK from handling the URL automatically
                // Return true to allow the SDK to handle the URL automatically, even
                // if you have done something custom
                return false
            }
        })
    }
}
