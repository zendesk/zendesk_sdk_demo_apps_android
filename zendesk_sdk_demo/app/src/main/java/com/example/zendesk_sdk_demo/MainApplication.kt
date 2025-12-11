package com.example.zendesk_sdk_demo

import android.app.Application
import android.util.Log
import androidx.core.content.ContextCompat
import zendesk.android.Zendesk
import zendesk.android.messaging.model.UserColors
import zendesk.messaging.android.DefaultMessagingFactory

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/getting_started/#3-initialize-the-sdk
        Zendesk.initialize(
            this,
            this.getString(R.string.channel_key),
            successCallback = { zendesk ->
                Log.i(LOG_TAG, getString(R.string.msg_init_success))
                // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/analytics_tracking
                // Enable/Disable analytics tracking, enabled by default
                zendesk.messaging.enableAnalyticsTracking(enabled = true)
            },
            failureCallback = { error ->
                // Tracking the cause of exceptions in your crash reporting dashboard will help to triage any unexpected failures in production
                Log.e(LOG_TAG, "${getString(R.string.msg_init_error)}: $error")
            },
            messagingFactory = DefaultMessagingFactory(
                userLightColors = UserColors(
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
                ),
                userDarkColors = UserColors(
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
                ),
            ),
        )
    }

    companion object {
        private val LOG_TAG = "[${Companion::class.java.simpleName}]"

        /**
         * Private reference to the instance of [MainApplication], initialized during [onCreate].
         */
        private lateinit var instance: MainApplication

        /**
         * Returns this instance of [MainApplication].
         */
        fun get(): MainApplication = instance
    }
}
