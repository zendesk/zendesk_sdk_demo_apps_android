package com.example.zendesk_sdk_navigation_api_demo.data

import android.content.Context
import android.util.Log
import com.example.zendesk_sdk_navigation_api_demo.R
import zendesk.android.Zendesk
import zendesk.android.messaging.MessagingScreen
import zendesk.messaging.android.DefaultMessagingFactory
import javax.inject.Inject

class ZendeskManager @Inject constructor() {

    private val LOG_TAG = "[${ZendeskManager::class.java.simpleName}]"

    fun initialise(context: Context) {
        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/getting_started/#initialize-the-sdk
        Zendesk.Companion.initialize(context, context.getString(R.string.channel_key), successCallback = { zendesk ->
            Log.i(LOG_TAG, context.getString(R.string.msg_init_success))
        }, failureCallback = { error ->
            // Tracking the cause of exceptions in your crash reporting dashboard will help to triage any unexpected failures in production
            Log.e(LOG_TAG, "${context.getString(R.string.msg_init_error)}: $error")
        }, messagingFactory = DefaultMessagingFactory())
    }

    fun showMessaging(context: Context, messagingScreen: MessagingScreen) {
        // Documentation: https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/multi_conversations_navigation_apis/
        Zendesk.Companion.instance.messaging.showMessaging(
            context = context,
            screen = messagingScreen
        )
    }
}