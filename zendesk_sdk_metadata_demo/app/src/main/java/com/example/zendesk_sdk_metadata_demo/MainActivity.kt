package com.example.zendesk_sdk_metadata_demo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import zendesk.android.Zendesk
import zendesk.logger.Logger

class MainActivity : AppCompatActivity() {

    private val LOG_TAG = "[${this.javaClass.name}]"

    private var coordinatorLayout: CoordinatorLayout? = null

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.topAppBar))
        coordinatorLayout = findViewById(R.id.coordinatorLayout)

        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/getting_started/#troubleshooting
        Logger.setLoggable(true)

        findViewById<Button>(R.id.InitButton).isVisible = false

        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/getting_started/#show-the-conversation
        findViewById<Button>(R.id.StartButton).setOnClickListener {
            Zendesk.instance.messaging.showMessaging(this)
        }

        //https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/advanced_integration/#conversation-fields
        findViewById<Button>(R.id.MetadataAddFieldsButton).setOnClickListener {
            addConversationFields()
        }
        findViewById<Button>(R.id.MetadataClearFieldsButton).setOnClickListener {
            clearConversationFields()
        }

        // https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/advanced_integration/#conversation-tags
        findViewById<Button>(R.id.MetadataAddTagsButton).setOnClickListener {
            addConversationTags()
        }
        findViewById<Button>(R.id.MetadataClearTagsButton).setOnClickListener {
            clearConversationTags()
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.about -> {
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.about_title)
                .setMessage(R.string.about_message)
                .setPositiveButton("OK", null)
                .show()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    // SDK related methods

    private fun addConversationFields() {
        //https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/advanced_integration/#set-conversation-fields
        val fields = mapOf("1234567890" to "value of the field")
        Zendesk.instance.messaging.setConversationFields(fields)
        coordinatorLayout?.let { layout ->
            Snackbar.make(layout, getString(R.string.msg_add_fields), Snackbar.LENGTH_LONG).show()
        }
    }
    private fun clearConversationFields() {
        //https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/advanced_integration/#clear-conversation-fields
        Zendesk.instance.messaging.clearConversationFields()
        coordinatorLayout?.let { layout ->
            Snackbar.make(layout, getString(R.string.msg_clear_fields), Snackbar.LENGTH_LONG).show()
        }
    }

    private fun addConversationTags() {
        //https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/advanced_integration/#set-conversation-tags
        val tags = listOf("tag", "tag_from_demo")
        Zendesk.instance.messaging.setConversationTags(tags)
        coordinatorLayout?.let { layout ->
            Snackbar.make(layout, getString(R.string.msg_add_tags), Snackbar.LENGTH_LONG).show()
        }
    }
    private fun clearConversationTags() {
        //https://developer.zendesk.com/documentation/zendesk-web-widget-sdks/sdks/android/advanced_integration/#clear-conversation-tags
        Zendesk.instance.messaging.clearConversationTags()
        coordinatorLayout?.let { layout ->
            Snackbar.make(layout, getString(R.string.msg_clear_tags), Snackbar.LENGTH_LONG).show()
        }
    }
}
