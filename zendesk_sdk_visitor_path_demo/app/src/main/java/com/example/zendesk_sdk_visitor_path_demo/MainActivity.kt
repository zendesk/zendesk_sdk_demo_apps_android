package com.example.zendesk_sdk_visitor_path_demo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import zendesk.android.Zendesk
import zendesk.android.pageviewevents.PageView
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

        findViewById<MaterialCardView>(R.id.InitCard).isVisible = false

        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/getting_started/#4-show-messaging
        findViewById<Button>(R.id.StartButton).setOnClickListener {
            Zendesk.instance.messaging.showMessaging(this)
        }

        findViewById<Button>(R.id.sendPageEventButton).setOnClickListener {
            // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/page_view_events/#sending-page-view-events
            addVisitorPath()
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

    // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/page_view_events/#sending-page-view-events
    private fun addVisitorPath(
        url: String = "android:app",
        pageTitle: String = "Android landing page"
    ) {
    // Create a `PageView` object
    val pageView = PageView(url = url, pageTitle = pageTitle)
    Log.d(LOG_TAG, "addVisitorPath: $pageView")
        Zendesk.instance.sendPageView(
            pageView,
            successCallback = {
                // Your success handling
                Log.d(LOG_TAG, getString(R.string.log_visitor_path_success))
            },
            failureCallback = { error ->
                // Your error handling
                Log.d(LOG_TAG, getString(R.string.log_visitor_path_error, error.message))
            },
        )
    }
}
