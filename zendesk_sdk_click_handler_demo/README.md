## Introduction

This is app is intended to demonstrate the [Clickable links delegate](https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/click_delegates) of the Zendesk SDK.

## Step to use the app

* Update the `channel_key` resource in [`strings.xml`](./app/src/main/res/values/strings.xml) with your own account channel key.   
You can find your channel key by following [those steps](https://support.zendesk.com/hc/en-us/articles/1260801714930).
* Launch the app.
* Initialize Zendesk.
* Access the conversation.
* Send or receive a url .
Only URLs such as `https://www.zendesk.com/` are made clickable in the UI. 
* Click on a url.

