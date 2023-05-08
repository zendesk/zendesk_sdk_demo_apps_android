## Introduction

This is app is intended to demonstrate the [Push Capabilities](https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/push_notifications/) of the Zendesk SDK.

The app contains a standard implementation of the `FirebaseMessagingService`.

## Step to use the app

* Update the `channel_key` resource in [`strings.xml`](./app/src/main/res/values/strings.xml) with your own account channel key.   
You can find your channel key by following [those steps](https://support.zendesk.com/hc/en-us/articles/1260801714930).
* Configure your [Messaging Channel to use Firebase](https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/push_notifications/#step-2---adding-your-firebase-key-and-id-in-the-zendesk-admin-center).
* Add your `google-services.json` file in the `app` folder.
Be sure that the `package_name` in the file is `com.example.zendesk_sdk_push_demo`.

* Launch the app.
* Allow the app to send you push notifications.
* Initialize Zendesk.
* Register the device by tapping the `Register push notifications` button.
* Access the conversation.
* Background the application.
* Receive a message in the conversation.

## Different implementations

### [Default implementation](https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/push_notifications/#using-the-default-implementation-of-firebasemessagingservice)

The Zendesk SDK for Android provides a default implementation of `FirebaseMessagingService`.

To use it, make sure that the `<service>` contained under "<!-- Use the SDK default messaging service -->" is commented out and
that the `<service>` contained under "<!-- Use the the custom messaging service -->" is commented.

### [Custom implementation](https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/push_notifications/#using-a-custom-implementation-of-firebasemessagingservice)

The app contains a custom implementation of `FirebaseMessagingService` in the `MyFirebaseMessagingService.kt` file.

To use it, make sure that the `<service>` contained under "<!-- Use the the custom messaging service -->" is commented out and
that the `<service>` contained under "<!-- Use the SDK default messaging service -->" is commented.


