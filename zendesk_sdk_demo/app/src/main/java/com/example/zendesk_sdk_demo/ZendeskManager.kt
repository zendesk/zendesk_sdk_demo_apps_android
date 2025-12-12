package com.example.zendesk_sdk_demo

import android.content.Context
import android.widget.Toast
import zendesk.android.Zendesk
import zendesk.android.messaging.MessagingFactory
import zendesk.logger.Logger

object ZendeskManager {

    fun initialize(
        context: Context,
        channelKey: String,
        factory: MessagingFactory,
    ) {
        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/getting_started/#3-initialize-the-sdk
        Zendesk.initialize(
            context = context,
            channelKey = channelKey,
            successCallback = { zendesk ->
                Logger.d(LOG_TAG, "Initialization Success", zendesk)
                Toast.makeText(
                    context,
                    "Initialization Success", Toast.LENGTH_SHORT,
                ).show()
            },
            failureCallback = {
                Toast.makeText(
                    context,
                    "Initialization failed", Toast.LENGTH_SHORT,
                ).show()
                Logger.d(LOG_TAG, "Zendesk initialization failed", it)
            },
            messagingFactory = factory
        )
    }

    fun showMessaging(context: Context) {
        Zendesk.instance.messaging.showMessaging(context = context)
    }

    fun invalidate() {
        Zendesk.invalidate()
    }

    private const val LOG_TAG = "ZendeskManager"
}