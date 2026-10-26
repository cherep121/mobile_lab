package com.example.unscramble_lab6.ui

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unscramble_lab6.R
import com.example.unscramble_lab6.data.MAX_NO_OF_WORDS

@Composable
fun GameScreen(viewModel: GameViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    GameContent(
        uiState = uiState,
        onUserGuessChanged = viewModel::updateUserGuess,
        onSubmit = viewModel::checkUserGuess,
        onSkip = viewModel::skipWord,
        onPlayAgain = viewModel::resetGame,
    )
}

@Composable
fun GameContent(
    uiState: GameUiState,
    onUserGuessChanged: (String) -> Unit,
    onSubmit: () -> Unit,
    onSkip: () -> Unit,
    onPlayAgain: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().safeDrawingPadding().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.word_count, uiState.currentWordCount, MAX_NO_OF_WORDS),
                    modifier = Modifier.align(Alignment.End),
                    style = MaterialTheme.typography.labelLarge,
                )
                Text(
                    text = uiState.currentScrambledWord,
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = stringResource(R.string.instructions),
                    textAlign = TextAlign.Center,
                )
                OutlinedTextField(
                    value = uiState.userGuess,
                    onValueChange = onUserGuessChanged,
                    label = { Text(stringResource(R.string.enter_your_word)) },
                    supportingText = {
                        if (uiState.isGuessedWordWrong) Text(stringResource(R.string.wrong_guess))
                    },
                    isError = uiState.isGuessedWordWrong,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { onSubmit() }),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
        Button(onClick = onSubmit, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.submit))
        }
        OutlinedButton(onClick = onSkip, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.skip))
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = stringResource(R.string.score, uiState.score),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }

    if (uiState.isGameOver) {
        FinalScoreDialog(score = uiState.score, onPlayAgain = onPlayAgain)
    }
}

@Composable
private fun FinalScoreDialog(score: Int, onPlayAgain: () -> Unit) {
    val activity = LocalContext.current as Activity
    AlertDialog(
        onDismissRequest = {},
        title = { Text(stringResource(R.string.congratulations)) },
        text = { Text(stringResource(R.string.you_scored, score)) },
        dismissButton = {
            TextButton(onClick = activity::finish) { Text(stringResource(R.string.exit)) }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) { Text(stringResource(R.string.play_again)) }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun GamePreview() {
    MaterialTheme {
        GameContent(
            uiState = GameUiState(currentScrambledWord = "elpmase"),
            onUserGuessChanged = {},
            onSubmit = {},
            onSkip = {},
            onPlayAgain = {},
        )
    }
}
