## Introduction

This is app is intended to demonstrate the [Authenticated experience](https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/advanced_integration/#authentication) of the Zendesk SDK.

This app also include the [Authentication event listener](https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/advanced_integration/#eventlistener).

## Step to use the app

* Update the `channel_key` resource in [`strings.xml`](./app/src/main/res/values/strings.xml) with your own account channel key.   
You can find your channel key by following [those steps](https://support.zendesk.com/hc/en-us/articles/1260801714930).
* Configure [User Authentication](https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/web/enabling_auth_visitors/#workflow) for Messaging in your Admin Center.
* Update the `jwt_token` resource in [`strings.xml`](./app/src/main/res/values/strings.xml) with a valid JWT token.
* Launch the app.
* Initialize Zendesk.
* Log in to start the authenticated user experience.
* Access the conversation.
