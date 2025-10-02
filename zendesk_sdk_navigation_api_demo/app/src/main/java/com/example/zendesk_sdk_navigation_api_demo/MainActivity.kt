package com.example.zendesk_sdk_navigation_api_demo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.zendesk_sdk_navigation_api_demo.ui.presentation.DemoScreen
import com.example.zendesk_sdk_navigation_api_demo.ui.theme.ZendeskTheme
import androidx.core.view.WindowCompat
import androidx.core.view.WindowCompat.enableEdgeToEdge
import com.example.zendesk_sdk_navigation_api_demo.data.DemoCardsResolver
import com.example.zendesk_sdk_navigation_api_demo.data.ZendeskManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import zendesk.logger.Logger
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var zendeskManager: ZendeskManager

    @Inject
    lateinit var demoCardsResolver: DemoCardsResolver

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Logger.setLoggable(true)

        enableEdgeToEdge(window)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ZendeskTheme {
                DemoScreen(
                    modifier = Modifier
                        .navigationBarsPadding(),
                    cards = demoCardsResolver.createDemoCards(context = LocalContext.current),
                    onNavigationIconClick = {
                        MaterialAlertDialogBuilder(this)
                            .setTitle(R.string.about_title)
                            .setMessage(R.string.about_message)
                            .setPositiveButton("OK", null)
                            .show()
                    }
                )
            }
        }
    }
}
