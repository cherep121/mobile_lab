package com.example.unscramble_lab6.ui

import androidx.lifecycle.ViewModel
import com.example.unscramble_lab6.data.MAX_NO_OF_WORDS
import com.example.unscramble_lab6.data.SCORE_INCREASE
import com.example.unscramble_lab6.data.allWords
import kotlin.random.Random
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel(
    private val words: Set<String> = allWords,
    private val random: Random = Random.Default,
) : ViewModel() {
    private val usedWords = mutableSetOf<String>()
    private lateinit var currentWord: String

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    init {
        resetGame()
    }

    fun updateUserGuess(guessedWord: String) {
        _uiState.update { it.copy(userGuess = guessedWord) }
    }

    fun checkUserGuess() {
        if (_uiState.value.userGuess.equals(currentWord, ignoreCase = true)) {
            updateGameState(_uiState.value.score + SCORE_INCREASE)
        } else {
            _uiState.update { it.copy(isGuessedWordWrong = true) }
        }
    }

    fun skipWord() {
        updateGameState(_uiState.value.score)
    }

    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickNextWord())
    }

    private fun updateGameState(updatedScore: Int) {
        if (usedWords.size == MAX_NO_OF_WORDS) {
            _uiState.update {
                it.copy(
                    score = updatedScore,
                    isGuessedWordWrong = false,
                    isGameOver = true,
                    userGuess = "",
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    currentScrambledWord = pickNextWord(),
                    currentWordCount = it.currentWordCount + 1,
                    score = updatedScore,
                    isGuessedWordWrong = false,
                    userGuess = "",
                )
            }
        }
    }

    private fun pickNextWord(): String {
        currentWord = words.filterNot(usedWords::contains).random(random)
        usedWords += currentWord
        return currentWord.reversed()
    }
}
