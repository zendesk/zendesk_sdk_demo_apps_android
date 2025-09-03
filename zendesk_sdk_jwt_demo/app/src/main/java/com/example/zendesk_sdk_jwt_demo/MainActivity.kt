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
import androidx.core.view.isVisible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import zendesk.android.Zendesk
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
        coordinatorLayout = findViewById<CoordinatorLayout>(R.id.coordinatorLayout)

        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/troubleshooting
        Logger.setLoggable(true)

        val initButton = findViewById<Button>(R.id.InitButton)
        initButton.isVisible = false

        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/getting_started/#4-show-messaging
        val startButton = findViewById<Button>(R.id.StartButton)
        startButton.setOnClickListener {
            Zendesk.instance.messaging.showMessaging(this)
        }

        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/authentication/#loginuser
        val loginButton = findViewById<Button>(R.id.LoginButton)
        loginButton.setOnClickListener {
            login()
        }

        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/authentication/#logoutuser
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

    private fun login() {
        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/authentication/#loginuser
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
        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/authentication/#logoutuser
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
}