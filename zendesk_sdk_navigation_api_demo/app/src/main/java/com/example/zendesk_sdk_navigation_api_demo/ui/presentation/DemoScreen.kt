package com.example.zendesk_sdk_navigation_api_demo.ui.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zendesk_sdk_navigation_api_demo.R
import com.example.zendesk_sdk_navigation_api_demo.data.DemoCardInfo
import com.example.zendesk_sdk_navigation_api_demo.ui.theme.ZendeskTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun DemoScreen(
    cards: ImmutableList<DemoCardInfo>,
    onNavigationIconClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var backNavigationStates by rememberSaveable(cards.size) { mutableStateOf(List(cards.size) { false }) }

    GradientBackground(
        modifier = modifier
            .padding(WindowInsets.statusBars.asPaddingValues()),
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Header(
                    onIconClick = {
                        onNavigationIconClick()
                    }
                )
                Spacer(
                    modifier = Modifier.padding(16.dp)
                )
            }

            itemsIndexed(cards) { index, card ->
                DemoCard(
                    title = card.title,
                    description = card.description,
                    buttonText = card.buttonText,
                    onButtonClick = { card.onAction(backNavigationStates[index]) },
                    enableBackNavigationUi = card.enableBackNavigationUi,
                    backNavigationEnabled = backNavigationStates[index],
                    enableWarningLabel = card.enableWarningLabel,
                    onBackNavigationChange = { checked ->
                        backNavigationStates = backNavigationStates.toMutableList().also {
                            it[index] = checked
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun ContentPreview() {
    ZendeskTheme {
        val context = LocalContext.current
        DemoScreen(
            cards = persistentListOf(
                DemoCardInfo(
                    title = context.getString(R.string.recently_active_conversation_title),
                    description = context.getString(R.string.recently_active_conversation_description),
                    buttonText = context.getString(R.string.go_to_conversation),
                    onAction = {}
                ),
                DemoCardInfo(
                    title = context.getString(R.string.conversation_title),
                    description = context.getString(R.string.conversation_description),
                    buttonText = context.getString(R.string.go_to_conversation),
                    enableWarningLabel = true,
                    onAction = { }
                ),
                DemoCardInfo(
                    title = context.getString(R.string.conversation_list_title),
                    description = context.getString(R.string.conversation_list_description),
                    buttonText = context.getString(R.string.go_to_conversation),
                    enableBackNavigationUi = false,
                    onAction = { }
                ),
            ),
            onNavigationIconClick = {},
        )
    }
}
