package com.example.zendesk_sdk_demo

import android.app.Application
import zendesk.messaging.android.DefaultMessagingFactory

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ZendeskManager.initialize(
            context = this,
            channelKey = this.getString(R.string.channel_key),
            factory = DefaultMessagingFactory(),
        )
    }
}
