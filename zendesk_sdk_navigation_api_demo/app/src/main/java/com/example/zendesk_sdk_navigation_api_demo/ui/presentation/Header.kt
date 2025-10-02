package com.example.zendesk_sdk_navigation_api_demo.ui.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.zendesk_sdk_navigation_api_demo.R
import com.example.zendesk_sdk_navigation_api_demo.ui.theme.ZendeskTheme

@Composable
fun Header(
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = context.getString(R.string.header_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Center)
        )
        Icon(
            modifier = modifier
                .align(Alignment.CenterEnd)
                .clickable { onIconClick() },
            painter = painterResource(id = R.drawable.ic_outline_error_outline_24),
            contentDescription = context.getString(R.string.header_icon_description)
        )
    }
}

@Preview
@Composable
private fun HeaderPreview() {
    ZendeskTheme {
        Surface {
            Header(
                onIconClick = {}
            )
        }

    }
}