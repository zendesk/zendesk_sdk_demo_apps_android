package com.example.zendesk_sdk_navigation_api_demo.data

import android.content.Context
import com.example.zendesk_sdk_navigation_api_demo.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import zendesk.android.messaging.MessagingScreen
import javax.inject.Inject

class DemoCardsResolver @Inject constructor(private val zendeskManager: ZendeskManager) {

    fun createDemoCards(context: Context): ImmutableList<DemoCardInfo> {
        return buildDemoCards(
            context = context,
            onNavigateToConversationList = {
                // Documentation: https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/multi_conversations_navigation_apis/#conversationslist
                zendeskManager.showMessaging(
                    context = context,
                    messagingScreen = MessagingScreen.ConversationsList
                )
            },
            onCreateConversation = { enableBackNavigation ->
                // Documentation: https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/multi_conversations_navigation_apis/#newconversation
                val exitAction = if (enableBackNavigation) {
                    MessagingScreen.ExitAction.Close
                } else {
                    MessagingScreen.ExitAction.ReturnToConversationList
                }
                zendeskManager.showMessaging(
                    context = context,
                    messagingScreen = MessagingScreen.NewConversation(
                        onExit = exitAction
                    )
                )
            },
            onNavigateToConversationScreen = { enableBackNavigation ->
                // Documentation: https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/multi_conversations_navigation_apis/#conversation
                val exitAction = if (enableBackNavigation) {
                    MessagingScreen.ExitAction.Close
                } else {
                    MessagingScreen.ExitAction.ReturnToConversationList
                }
                zendeskManager.showMessaging(
                    context = context,
                    messagingScreen = MessagingScreen.Conversation(
                        id = "{conversationId}", // Replace with a valid conversation ID to test this screen
                        onExit = exitAction
                    )
                )
            },
            onNavigateToRecentConversation = { enableBackNavigation ->
                // Documentation: https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/multi_conversations_navigation_apis/#mostrecentactiveconversation
                val exitAction = if (enableBackNavigation) {
                    MessagingScreen.ExitAction.Close
                } else {
                    MessagingScreen.ExitAction.ReturnToConversationList
                }
                zendeskManager.showMessaging(
                    context = context,
                    messagingScreen = MessagingScreen.MostRecentActiveConversation(
                        onExit = exitAction
                    )
                )
            },
        )
    }

    private fun buildDemoCards(
        context: Context,
        onNavigateToConversationList: (Boolean) -> Unit,
        onNavigateToConversationScreen: (Boolean) -> Unit,
        onNavigateToRecentConversation: (Boolean) -> Unit,
        onCreateConversation: (Boolean) -> Unit,
    ): ImmutableList<DemoCardInfo> {
        return persistentListOf(
            DemoCardInfo(
                title = context.getString(R.string.recently_active_conversation_title),
                description = context.getString(R.string.recently_active_conversation_description),
                buttonText = context.getString(R.string.go_to_conversation),
                onAction = { backNavEnabled ->
                    onNavigateToRecentConversation(backNavEnabled)
                }
            ),
            DemoCardInfo(
                title = context.getString(R.string.conversation_list_title),
                description = context.getString(R.string.conversation_list_description),
                buttonText = context.getString(R.string.go_to_conversation),
                enableBackNavigationUi = false,
                onAction = { backNavEnabled ->
                    onNavigateToConversationList(backNavEnabled)
                }
            ),
            DemoCardInfo(
                title = context.getString(R.string.conversation_title),
                description = context.getString(R.string.conversation_description),
                buttonText = context.getString(R.string.go_to_conversation),
                enableWarningLabel = true,
                onAction = { backNavEnabled ->
                    onNavigateToConversationScreen(backNavEnabled)
                }
            ),
            DemoCardInfo(
                title = context.getString(R.string.create_conversation_title),
                description = context.getString(R.string.create_conversation_description),
                buttonText = context.getString(R.string.go_to_conversation),
                onAction = { backNavEnabled ->
                    onCreateConversation(backNavEnabled)
                }
            )
        )
    }
}