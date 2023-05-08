package com.example.zendesk_sdk_push_demo

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import zendesk.messaging.android.push.PushNotifications
import zendesk.messaging.android.push.PushResponsibility

// https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/push_notifications/
class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val LOG_TAG = "["+ this.javaClass.name +"]"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Log.d(LOG_TAG, "Push message received: $remoteMessage")
        when (PushNotifications.shouldBeDisplayed(remoteMessage.data)) {
            PushResponsibility.MESSAGING_SHOULD_DISPLAY -> {
                // This push belongs to Messaging and the SDK is able to display it to the end user
                // IF you wish to use your own displayNotification method, replace this line by your method
                PushNotifications.displayNotification(context = this, messageData = remoteMessage.data)
            }
            PushResponsibility.MESSAGING_SHOULD_NOT_DISPLAY -> {
                // This push belongs to Messaging but it should not be displayed to the end user
            }
            PushResponsibility.NOT_FROM_MESSAGING -> {
                // This push does not belong to Messaging
            }
        }

    }

    // Callback added for the confirmation SNACKBAR
    fun registerToken(
        successCallback: () -> Unit = {},
        failureCallback: () -> Unit = {}
    ) {
        // https://firebase.google.com/docs/cloud-messaging/android/client#retrieve-the-current-registration-token
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(LOG_TAG, "Fetching FCM registration token failed", task.exception)
                failureCallback()
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = "Fetching FCM registration token success : $token"
            Log.d(LOG_TAG, msg)

            // Register on Zendesk
            PushNotifications.updatePushNotificationToken(token)

            successCallback()

        })
    }

    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(newToken: String) {
        PushNotifications.updatePushNotificationToken(newToken)
    }

}