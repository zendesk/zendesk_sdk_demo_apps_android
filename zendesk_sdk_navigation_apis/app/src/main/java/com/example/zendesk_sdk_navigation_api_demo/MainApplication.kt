package com.example.zendesk_sdk_navigation_api_demo

import android.app.Application
import com.example.zendesk_sdk_navigation_api_demo.data.ZendeskManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {

    @Inject
    lateinit var zendeskManager: ZendeskManager

    override fun onCreate() {
        super.onCreate()

        zendeskManager.initialise(this)

        instance = this
    }

    companion object {
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
