package com.example.zendesk_sdk_jwt_demo

import android.app.Application
import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import zendesk.android.Zendesk
import zendesk.android.ZendeskAuthenticationDelegate
import zendesk.android.events.ZendeskEvent
import zendesk.android.events.ZendeskEventListener
import zendesk.messaging.android.DefaultMessagingFactory

class MainApplication : Application() {

    private var delegateScope = MainScope()

    private var retries = 0

    override fun onCreate() {
        super.onCreate()
        setupDelegate(
            context = this,
            successfulRetry = 3,
            delay = 2000L,
            jwt = this.resources.getString(R.string.jwt_token),
        )
        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/getting_started/#3-initialize-the-sdk
        Zendesk.initialize(this, this.getString(R.string.channel_key), successCallback = { zendesk ->
            Log.i(LOG_TAG, getString(R.string.msg_init_success))
            addEventListener()
        }, failureCallback = { error ->
            // Tracking the cause of exceptions in your crash reporting dashboard will help to triage any unexpected failures in production
            Log.e(LOG_TAG, "${getString(R.string.msg_init_error)}: $error")
        }, messagingFactory = DefaultMessagingFactory())
    }

    fun setupDelegate(
        context: Context,
        successfulRetry: Int,
        delay: Long,
        jwt: String,
    ) {
        retries = 0
        delegateScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        Zendesk.authenticationDelegate =
            ZendeskAuthenticationDelegate { _, updateToken ->
                delegateScope.launch {
                    // Simulate delay of fetching JWT
                    delay(delay)
                    updateToken(
                        if (retries >= successfulRetry.coerceIn(MIN_RETRY, MAX_RETRY)) {
                            jwt
                        } else {
                            context.resources.getString(R.string.jwt_expired)
                        },
                    )
                    retries++
                }
            }
    }

    private fun addEventListener() {
        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/zendesk_events/#eventlistener
        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/authentication/#handling-authentication-failures-with-zendeskeventauthenticationfailed
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

    companion object {
        private val LOG_TAG = "[${Companion::class.java.simpleName}]"
        private const val MIN_RETRY = 0
        private const val MAX_RETRY = 4

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
