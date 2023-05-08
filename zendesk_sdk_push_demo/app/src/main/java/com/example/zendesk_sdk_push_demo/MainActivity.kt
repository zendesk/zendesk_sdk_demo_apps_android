package com.example.zendesk_sdk_push_demo

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
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

        requestPushPermission()

        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/getting_started/#troubleshooting
        Logger.setLoggable(true)

        findViewById<Button>(R.id.InitButton).setOnClickListener {
            initializeZendesk()
        }

        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/getting_started/#show-the-conversation
        findViewById<Button>(R.id.StartButton).setOnClickListener {
            registerForPushIfEnabled()
            Zendesk.instance.messaging.showMessaging(this)
        }

        findViewById<Button>(R.id.RegisterPushButton).setOnClickListener {
            val myFirebaseService = MyFirebaseMessagingService()
            //myFirebaseService.registerToken()
            myFirebaseService.registerToken(
                successCallback = {
                    coordinatorLayout?.let {
                        Snackbar.make(it, getString(R.string.msg_push_registration_success), Snackbar.LENGTH_LONG).show()
                    }
                },
                failureCallback = {
                    coordinatorLayout?.let {
                        Snackbar.make(it, getString(R.string.msg_push_registration_error), Snackbar.LENGTH_LONG).show()
                    }
                }
            )
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

    private fun requestPushPermission() {
        if (ContextCompat.checkSelfPermission(applicationContext,
                Manifest.permission.POST_NOTIFICATIONS) != PERMISSION_GRANTED)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 200)
            }
    }

    private fun registerForPushIfEnabled() {
        if (ContextCompat.checkSelfPermission(applicationContext,
                Manifest.permission.POST_NOTIFICATIONS) == PERMISSION_GRANTED){
            MyFirebaseMessagingService().registerToken(
                successCallback = {
                    Log.d(LOG_TAG, getString(R.string.msg_push_registration_success))
                    coordinatorLayout?.let {
                        Snackbar.make(it, getString(R.string.msg_push_registration_success), Snackbar.LENGTH_LONG).show()
                    }
                },
                failureCallback = {
                    Log.d(LOG_TAG, getString(R.string.msg_push_registration_error))
                    coordinatorLayout?.let {
                        Snackbar.make(it, getString(R.string.msg_push_registration_error), Snackbar.LENGTH_LONG).show()
                    }
                }
            )
        } else {
            Log.d(LOG_TAG, getString(R.string.msg_push_disabled))
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