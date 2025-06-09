package com.example.zendesk_sdk_unread_message_demo

import android.app.Application
import android.util.Log
import zendesk.android.Zendesk
import zendesk.android.events.ZendeskEvent
import zendesk.android.events.ZendeskEventListener
import zendesk.messaging.android.DefaultMessagingFactory

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/getting_started/#initialize-the-sdk
        Zendesk.initialize(this, this.getString(R.string.channel_key), successCallback = { zendesk ->
            Log.i(LOG_TAG, getString(R.string.msg_init_success))
            addEventsListener()
        }, failureCallback = { error ->
            // Tracking the cause of exceptions in your crash reporting dashboard will help to triage any unexpected failures in production
            Log.e(LOG_TAG, "${getString(R.string.msg_init_error)}: $error")
        }, messagingFactory = DefaultMessagingFactory())
    }

    private fun addEventsListener() {

        // To create and use the event listener:
        val zendeskEventListener = ZendeskEventListener { zendeskEvent ->
            when (zendeskEvent) {
                is ZendeskEvent.UnreadMessageCountChanged -> {
                    Log.d(LOG_TAG, "[UnreadMessageCountChanged] - ${zendeskEvent.data.totalUnreadMessagesCount}")
                }
                else -> {
                    // Default branch for forward compatibility with Zendesk SDK and its `ZendeskEvent` expansion
                }
            }
        }
        // To add the event listener to your Zendesk instance:
        Zendesk.instance.addEventListener(zendeskEventListener)
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
