package com.example.activitylifecycle_lab6

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    private val lifecycleEvents = mutableStateListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recordLifecycleEvent("onCreate")
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.screen_title),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = stringResource(R.string.instructions),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                Text(
                                    text = stringResource(R.string.current_event),
                                    style = MaterialTheme.typography.labelLarge,
                                )
                                Text(
                                    text = lifecycleEvents.lastOrNull() ?: "-",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                )
                            }
                        }
                        Text(
                            text = stringResource(R.string.event_history),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                        )
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            itemsIndexed(lifecycleEvents) { index, event ->
                                Text("${index + 1}. $event")
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        recordLifecycleEvent("onStart")
    }

    override fun onResume() {
        super.onResume()
        recordLifecycleEvent("onResume")
    }

    override fun onPause() {
        recordLifecycleEvent("onPause")
        super.onPause()
    }

    override fun onStop() {
        recordLifecycleEvent("onStop")
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
        recordLifecycleEvent("onRestart")
    }

    override fun onDestroy() {
        recordLifecycleEvent("onDestroy")
        super.onDestroy()
    }

    private fun recordLifecycleEvent(event: String) {
        Log.d(LOG_TAG, event)
        lifecycleEvents += event
    }

    private companion object {
        const val LOG_TAG = "ActivityLifecycle"
    }
}
