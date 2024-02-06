package com.example.zendesk_sdk_jwt_demo

import android.app.Application
import android.util.Log
import zendesk.android.Zendesk
import zendesk.messaging.android.DefaultMessagingFactory

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/getting_started/#initialize-the-sdk
        Zendesk.initialize(this, this.getString(R.string.channel_key), successCallback = { zendesk ->
            Log.i(LOG_TAG, getString(R.string.msg_init_success))
        }, failureCallback = { error ->
            // Tracking the cause of exceptions in your crash reporting dashboard will help to triage any unexpected failures in production
            Log.e(LOG_TAG, "${getString(R.string.msg_init_error)}: $error")
        }, messagingFactory = DefaultMessagingFactory())
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
