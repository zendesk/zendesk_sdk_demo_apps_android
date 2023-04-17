package com.example.zendesk_sdk_demo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.WindowCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import zendesk.android.Zendesk
import zendesk.logger.Logger
import zendesk.messaging.android.DefaultMessagingFactory

class MainActivity : AppCompatActivity() {

    val LOG_TAG = "[${this.javaClass.name}]"

    private var coordinatorLayout: CoordinatorLayout? = null

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.topAppBar))
        coordinatorLayout = findViewById<CoordinatorLayout>(R.id.coordinatorLayout)

        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/getting_started/#troubleshooting
        Logger.setLoggable(true)

        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/getting_started/#initialize-the-sdk
        findViewById<Button>(R.id.InitButton).setOnClickListener {
            initializeZendesk()
        }

        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/getting_started/#show-the-conversation
        findViewById<Button>(R.id.StartButton).setOnClickListener {
            Zendesk.instance.messaging.showMessaging(this)
        }
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

    private fun initializeZendesk() {
        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/getting_started/#initialize-the-sdk
        Zendesk.initialize(this, this.getString(R.string.channel_key), successCallback = { zendesk ->
            Log.i(LOG_TAG, getString(R.string.msg_init_success))
            coordinatorLayout?.let {
                Snackbar.make(it, getString(R.string.msg_init_success), Snackbar.LENGTH_LONG).show()
            }
        }, failureCallback = { error ->
            // Tracking the cause of exceptions in your crash reporting dashboard will help to triage any unexpected failures in production
            Log.e(LOG_TAG, "${getString(R.string.msg_init_error)}: $error")
            coordinatorLayout?.let {
                Snackbar.make(it, getString(R.string.msg_init_error), Snackbar.LENGTH_LONG).show()
            }
        }, messagingFactory = DefaultMessagingFactory())
    }
}
