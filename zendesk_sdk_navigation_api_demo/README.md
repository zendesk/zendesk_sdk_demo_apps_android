## Introduction

This app is intended to demonstrate the [Multi-Conversations Navigation APIs](https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/multi_conversations_navigation_apis/) of the Zendesk SDK. The navigation APIs allow you to control how users navigate between different messaging screens and conversations.

## Features Demonstrated

This app showcases the following navigation options:
- Navigate to the most recently active conversation
- Navigate to the conversation list screen  
- Navigate to a specific conversation screen
- Create a new conversation
- Control back navigation behavior (close vs return to conversation list)

## Setup Instructions

1. **Update Channel Key**: Update the `channel_key` resource in [`strings.xml`](./app/src/main/res/values/strings.xml) with your own account channel key.   
   You can find your channel key by following [these steps](https://support.zendesk.com/hc/en-us/articles/1260801714930).

2. **Initialize Zendesk**: The app automatically initializes the Zendesk SDK on launch.

## How to Use the App

1. **Launch the app** - The Zendesk SDK will be automatically initialized.

2. **Explore Navigation Options** - The main screen displays four cards, each demonstrating different navigation APIs:
   - **Recently Active Conversation**: Navigate to the most recently active conversation screen
   - **Conversation List**: Navigate to the conversation list screen  
   - **Conversation**: Navigate to a specific conversation screen
   - **Create New Conversation**: Navigate to a newly created conversation screen

3. **Configure Back Navigation** - For applicable cards, toggle the "Enable back navigation" option to control the exit behavior:
   - **Enabled**: Users can navigate back to your app
   - **Disabled**: Users return to the conversation list when exiting

4. **Test Navigation** - Tap any "Go to Conversation" button to test the respective navigation API.

## Navigation API Details

The app demonstrates the following `MessagingScreen` types:

- `MessagingScreen.MostRecentActiveConversation()` - Shows the most recent conversation
- `MessagingScreen.ConversationsList` - Shows all conversations  
- `MessagingScreen.Conversation(id, onExit)` - Shows a specific conversation
- `MessagingScreen.NewConversation(onExit)` - Creates and shows a new conversation

Each navigation option supports configurable exit actions:
- `MessagingScreen.ExitAction.Close` - Returns to your app
- `MessagingScreen.ExitAction.ReturnToConversationList` - Returns to conversation list
