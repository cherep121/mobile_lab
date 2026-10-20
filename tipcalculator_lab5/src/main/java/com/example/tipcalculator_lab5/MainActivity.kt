package com.example.tipcalculator_lab5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val TipColors = lightColorScheme(
    primary = Color(0xFF7D5260),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFD9E2),
    onPrimaryContainer = Color(0xFF31101D),
    background = Color(0xFFFFF8FA),
    surface = Color(0xFFFFF8FA),
    onSurface = Color(0xFF211A1C),
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = android.graphics.Color.TRANSPARENT,
                darkScrim = android.graphics.Color.TRANSPARENT,
            ),
        )
        setContent {
            MaterialTheme(colorScheme = TipColors) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TipCalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun TipCalculatorScreen(modifier: Modifier = Modifier) {
    var amountInput by rememberSaveable { androidx.compose.runtime.mutableStateOf("") }
    var tipPercentInput by rememberSaveable { androidx.compose.runtime.mutableStateOf("") }
    var roundUp by rememberSaveable { androidx.compose.runtime.mutableStateOf(false) }

    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tipPercent = tipPercentInput.toDoubleOrNull() ?: 0.0
    val tip = calculateTip(amount, tipPercent, roundUp)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding()
            .imePadding()
            .padding(horizontal = 32.dp, vertical = 28.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = stringResource(R.string.helper_text),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.68f),
            modifier = Modifier.padding(top = 6.dp, bottom = 28.dp),
        )
        EditNumberField(
            value = amountInput,
            onValueChange = { amountInput = it },
            label = stringResource(R.string.bill_amount),
            leadingText = stringResource(R.string.currency_marker),
            imeAction = ImeAction.Next,
        )
        Spacer(modifier = Modifier.height(16.dp))
        EditNumberField(
            value = tipPercentInput,
            onValueChange = { tipPercentInput = it },
            label = stringResource(R.string.tip_percentage),
            leadingText = "%",
            imeAction = ImeAction.Done,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.round_up_tip),
                style = MaterialTheme.typography.bodyLarge,
            )
            Switch(checked = roundUp, onCheckedChange = { roundUp = it })
        }
        Text(
            text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 30.dp),
        )
    }
}

@Composable
private fun EditNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingText: String,
    imeAction: ImeAction,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        label = { Text(label) },
        leadingIcon = { Text(leadingText, fontWeight = FontWeight.Bold) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction,
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() },
        ),
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TipCalculatorPreview() {
    MaterialTheme(colorScheme = TipColors) { TipCalculatorScreen() }
}
