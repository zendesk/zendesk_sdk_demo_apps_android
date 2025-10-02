package com.example.zendesk_sdk_navigation_api_demo.data

data class DemoCardInfo(
    val title: String,
    val description: String,
    val buttonText: String,
    val onAction: (Boolean) -> Unit,
    val enableBackNavigationUi: Boolean = true,
)
