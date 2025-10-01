package com.example.zendesk_sdk_navigation_api_demo.ui.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zendesk_sdk_navigation_api_demo.ui.theme.ZendeskTheme

@Composable
fun DemoCard(
    title: String,
    description: String,
    buttonText: String,
    backNavigationEnabled: Boolean,
    onBackNavigationChange: (Boolean) -> Unit,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    enableBackNavigationUi: Boolean = true,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Text(text = description, style = MaterialTheme.typography.bodyMedium)
            if (enableBackNavigationUi) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = backNavigationEnabled,
                        onCheckedChange = onBackNavigationChange
                    )
                    Text(
                        text = "Enable Back Navigation",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = onButtonClick) {
                    Text(text = buttonText)
                }
            }

        }
    }
}

@Preview
@Composable
private fun DemoCardPreview() {
    ZendeskTheme {
        DemoCard(
            title = "Demo Title",
            description = "This is a description of the demo card.",
            buttonText = "Click Me",
            backNavigationEnabled = true,
            onBackNavigationChange = {},
            onButtonClick = {}
        )
    }
}