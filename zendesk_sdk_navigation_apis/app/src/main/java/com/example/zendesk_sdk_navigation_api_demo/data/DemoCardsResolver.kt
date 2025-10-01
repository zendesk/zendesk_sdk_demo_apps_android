package com.example.zendesk_sdk_navigation_api_demo.data

import android.content.Context
import com.example.zendesk_sdk_navigation_api_demo.R
import com.example.zendesk_sdk_navigation_api_demo.data.ZendeskManager
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import zendesk.android.messaging.MessagingScreen
import javax.inject.Inject

class DemoCardsResolver @Inject constructor(private val zendeskManager: ZendeskManager) {

    fun createDemoCards(context: Context): ImmutableList<DemoCardInfo> {
        return buildDemoCards(
            context = context,
            onNavigateToConversationList = {
                zendeskManager.showMessaging(
                    context = context,
                    messagingScreen = MessagingScreen.ConversationsList
                )
            },
            onCreateConversation = { enableBackNavigation ->
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
                val exitAction = if (enableBackNavigation) {
                    MessagingScreen.ExitAction.Close
                } else {
                    MessagingScreen.ExitAction.ReturnToConversationList
                }
                zendeskManager.showMessaging(
                    context = context,
                    messagingScreen = MessagingScreen.Conversation(
                        id = "",
                        onExit = exitAction
                    )
                )
            },
            onNavigateToRecentConversation = { enableBackNavigation ->
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