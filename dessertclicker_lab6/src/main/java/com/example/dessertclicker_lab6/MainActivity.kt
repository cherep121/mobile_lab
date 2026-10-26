package com.example.dessertclicker_lab6

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) { DessertClickerRoute() }
            }
        }
    }
}

@Composable
fun DessertClickerRoute(viewModel: DessertClickerViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    DessertClickerScreen(
        uiState = uiState,
        onDessertClicked = viewModel::sellDessert,
        onShareClicked = { shareResults(context, uiState) },
    )
}

@Composable
fun DessertClickerScreen(
    uiState: DessertClickerUiState,
    onDessertClicked: () -> Unit,
    onShareClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize().safeDrawingPadding(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            Button(onClick = onShareClicked) { Text(stringResource(R.string.share)) }
        }

        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
            Image(
                painter = painterResource(R.drawable.bakery_back),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
            Image(
                painter = painterResource(uiState.currentDessertImageId),
                contentDescription = stringResource(R.string.dessert_description),
                modifier = Modifier.size(190.dp).align(Alignment.Center).clickable(onClick = onDessertClicked),
            )
        }

        Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp)) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                ResultRow(stringResource(R.string.desserts_sold), uiState.dessertsSold.toString())
                ResultRow(stringResource(R.string.total_revenue), "\$${uiState.revenue}")
            }
        }
    }
}

@Composable
private fun ResultRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.titleMedium)
        Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
    }
}

private fun shareResults(context: Context, uiState: DessertClickerUiState) {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(
            Intent.EXTRA_TEXT,
            context.getString(R.string.share_text, uiState.dessertsSold, uiState.revenue),
        )
    }
    try {
        ContextCompat.startActivity(context, Intent.createChooser(sendIntent, null), null)
    } catch (_: ActivityNotFoundException) {
        Toast.makeText(context, R.string.sharing_not_available, Toast.LENGTH_LONG).show()
    }
}

@Preview(showBackground = true)
@Composable
private fun DessertClickerPreview() {
    MaterialTheme {
        DessertClickerScreen(
            uiState = DessertClickerUiState(
                currentDessertImageId = R.drawable.cupcake,
                currentDessertPrice = 5,
            ),
            onDessertClicked = {},
            onShareClicked = {},
        )
    }
}
