package com.example.zendesk_sdk_demo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import zendesk.android.messaging.model.UserColors
import zendesk.logger.Logger
import zendesk.messaging.android.DefaultMessagingFactory
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var zendeskManager: ZendeskManager

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
            zendeskManager.showMessaging(context = this)
        }

        findViewById<Button>(R.id.ChangeColorsButton).setOnClickListener {
            zendeskManager.invalidate()
            zendeskManager.initialize(
                context = this,
                channelKey = this.getString(R.string.channel_key),
                factory = DefaultMessagingFactory(
                    userLightColors = lightColors(),
                    userDarkColors = darkColors(),
                )
            )
        }
    }

    private fun lightColors(): UserColors = UserColors(
        primary = ContextCompat.getColor(
            this,
            R.color.user_primary_light,
        ),
        onPrimary = ContextCompat.getColor(
            this,
            R.color.user_on_primary_light,
        ),
        action = ContextCompat.getColor(
            this,
            R.color.user_action_light,
        ),
        onAction = ContextCompat.getColor(
            this,
            R.color.user_on_action_light,
        ),
        onMessage = ContextCompat.getColor(
            this,
            R.color.user_on_message_light,
        ),
        message = ContextCompat.getColor(
            this,
            R.color.user_message_light,
        ),
        onBusinessMessage = ContextCompat.getColor(
            this,
            R.color.user_on_business_message_light,
        ),
        businessMessage = ContextCompat.getColor(
            this,
            R.color.user_business_message_light,
        ),
        background = ContextCompat.getColor(
            this,
            R.color.user_background_light,
        ),
        onBackground = ContextCompat.getColor(
            this,
            R.color.user_on_background_light,
        ),
        onSecondaryAction = ContextCompat.getColor(
            this,
            R.color.user_on_secondary_action_light,
        ),
        error = ContextCompat.getColor(
            this,
            R.color.user_error_light,
        ),
        notify = ContextCompat.getColor(
            this,
            R.color.user_notify_light,
        ),
        onError = ContextCompat.getColor(
            this,
            R.color.user_on_error_light,
        ),
        onNotify = ContextCompat.getColor(
            this,
            R.color.user_on_notify_light,
        ),
    )

    private fun darkColors(): UserColors = UserColors(
        primary = ContextCompat.getColor(
            this, R.color.user_primary_dark,
        ),
        onPrimary = ContextCompat.getColor(
            this,
            R.color.user_on_primary_dark,
        ),
        action = ContextCompat.getColor(
            this,
            R.color.user_action_dark,
        ),
        onAction = ContextCompat.getColor(
            this,
            R.color.user_on_action_dark,
        ),
        onMessage = ContextCompat.getColor(
            this,
            R.color.user_on_message_dark,
        ),
        message = ContextCompat.getColor(
            this,
            R.color.user_message_dark,
        ),
        onBusinessMessage = ContextCompat.getColor(
            this,
            R.color.user_on_business_message_dark,
        ),
        businessMessage = ContextCompat.getColor(
            this,
            R.color.user_business_message_dark,
        ),
        background = ContextCompat.getColor(
            this,
            R.color.user_background_dark,
        ),
        onBackground = ContextCompat.getColor(
            this,
            R.color.user_on_background_dark,
        ),
        onSecondaryAction = ContextCompat.getColor(
            this,
            R.color.user_on_secondary_action_dark,
        ),
        error = ContextCompat.getColor(
            this,
            R.color.user_error_dark,
        ),
        notify = ContextCompat.getColor(
            this,
            R.color.user_notify_dark,
        ),
        onError = ContextCompat.getColor(
            this,
            R.color.user_on_error_dark,
        ),
        onNotify = ContextCompat.getColor(
            this,
            R.color.user_on_notify_dark,
        ),
    )

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
}
