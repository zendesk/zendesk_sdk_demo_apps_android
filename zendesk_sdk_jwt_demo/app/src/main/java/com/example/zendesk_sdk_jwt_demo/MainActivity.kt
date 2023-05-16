package com.example.zendesk_sdk_jwt_demo

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
import zendesk.android.events.ZendeskEvent
import zendesk.android.events.ZendeskEventListener
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

        val initButton = findViewById<Button>(R.id.InitButton)
        initButton.setOnClickListener {
            initializeZendesk()
        }

        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/getting_started/#show-the-conversation
        val startButton = findViewById<Button>(R.id.StartButton)
        startButton.setOnClickListener {
            Zendesk.instance.messaging.showMessaging(this)
        }

        //https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/advanced_integration/#loginuser
        val loginButton = findViewById<Button>(R.id.LoginButton)
        loginButton.setOnClickListener {
            login()
        }

        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/advanced_integration/#logoutuser
        val logoutButton = findViewById<Button>(R.id.LogoutButton)
        logoutButton.setOnClickListener {
            logout()
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
            // Extra line to add the event listener only once the initialization is completed
            addEventListener()
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

    private fun login() {
        //https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/advanced_integration/#loginuser
        Zendesk.instance.loginUser(
            getString(R.string.jwt_token),
            successCallback = { it ->
                Log.d(LOG_TAG, getString(R.string.msg_login_success) + " : $it")
                coordinatorLayout?.let { layout ->
                    Snackbar.make(layout, getString(R.string.msg_login_success), Snackbar.LENGTH_LONG).show()
                }},
            failureCallback = {
                Log.e(LOG_TAG, getString(R.string.msg_login_error) + " : ${it.stackTraceToString()}")
                coordinatorLayout?.let { layout ->
                    Snackbar.make(layout, getString(R.string.msg_login_error), Snackbar.LENGTH_LONG).show()
                }}
        )
    }

    private fun logout() {
        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/advanced_integration/#logoutuser
        Zendesk.instance.logoutUser(
            successCallback = {
                Log.d(LOG_TAG, getString(R.string.msg_logout_success))
                coordinatorLayout?.let { layout ->
                    Snackbar.make(layout, getString(R.string.msg_logout_success), Snackbar.LENGTH_LONG).show()
                }
            },
            failureCallback = {
                Log.e(LOG_TAG, getString(R.string.msg_logout_error) + " : " + it.stackTraceToString())
                coordinatorLayout?.let { layout ->
                    Snackbar.make(layout, getString(R.string.msg_logout_error), Snackbar.LENGTH_LONG).show()
                }
            }
        )
    }

    private fun addEventListener() {
        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/advanced_integration/#eventlistener
        // To create and use the event listener:
        val zendeskEventListener = ZendeskEventListener { zendeskEvent ->
            when (zendeskEvent) {
                is ZendeskEvent.AuthenticationFailed -> {
                    Log.d(LOG_TAG, getString(R.string.msg_authentication_failed_event))
                }
                else -> {
                    // Default branch for forward compatibility with Zendesk SDK and its `ZendeskEvent` expansion
                }
            }
        }
        // To add the event listener to your Zendesk instance:
        // (safe for concurrent use)
        Zendesk.instance.addEventListener(zendeskEventListener)
        // To remove the event listener from your Zendesk instance:
        // (safe for concurrent use)
        //Zendesk.instance.removeEventListener(zendeskEventListener)
    }
}