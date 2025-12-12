package com.example.zendesk_sdk_demo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import zendesk.messaging.android.DefaultMessagingFactory
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {

    @Inject
    lateinit var zendeskManager: ZendeskManager

    override fun onCreate() {
        super.onCreate()

        zendeskManager.initialize(
            context = this,
            channelKey = this.getString(R.string.channel_key),
            factory = DefaultMessagingFactory(),
        )
    }
}
