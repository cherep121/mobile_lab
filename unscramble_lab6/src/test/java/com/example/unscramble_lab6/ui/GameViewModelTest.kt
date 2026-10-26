package com.example.unscramble_lab6.ui

import com.example.unscramble_lab6.data.MAX_NO_OF_WORDS
import com.example.unscramble_lab6.data.SCORE_INCREASE
import com.example.unscramble_lab6.data.allWords
import kotlin.random.Random
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GameViewModelTest {
    private lateinit var viewModel: GameViewModel

    @Before
    fun setUp() {
        viewModel = GameViewModel(words = allWords, random = Random(0))
    }

    @Test
    fun gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset() {
        val correctWord = unscramble(viewModel.uiState.value.currentScrambledWord)
        viewModel.updateUserGuess(correctWord)
        viewModel.checkUserGuess()

        val state = viewModel.uiState.value
        assertEquals(SCORE_INCREASE, state.score)
        assertFalse(state.isGuessedWordWrong)
    }

    @Test
    fun gameViewModel_IncorrectGuess_ErrorFlagSet() {
        viewModel.updateUserGuess("incorrect")
        viewModel.checkUserGuess()

        val state = viewModel.uiState.value
        assertTrue(state.isGuessedWordWrong)
        assertEquals(0, state.score)
        assertEquals(1, state.currentWordCount)
    }

    @Test
    fun gameViewModel_Initialization_FirstWordLoaded() {
        val state = viewModel.uiState.value

        assertTrue(state.currentScrambledWord.isNotBlank())
        assertEquals(1, state.currentWordCount)
        assertEquals(0, state.score)
        assertFalse(state.isGameOver)
    }

    @Test
    fun gameViewModel_AllWordsGuessed_UiStateUpdatedCorrectly() {
        repeat(MAX_NO_OF_WORDS) {
            val correctWord = unscramble(viewModel.uiState.value.currentScrambledWord)
            viewModel.updateUserGuess(correctWord)
            viewModel.checkUserGuess()
        }

        val completedState = viewModel.uiState.value
        assertTrue(completedState.isGameOver)
        assertEquals(MAX_NO_OF_WORDS, completedState.currentWordCount)
        assertEquals(MAX_NO_OF_WORDS * SCORE_INCREASE, completedState.score)

        viewModel.resetGame()
        val resetState = viewModel.uiState.value
        assertFalse(resetState.isGameOver)
        assertEquals(1, resetState.currentWordCount)
        assertEquals(0, resetState.score)
    }

    @Test
    fun gameViewModel_WordSkipped_ScoreUnchangedAndWordCountIncreased() {
        val firstWord = viewModel.uiState.value.currentScrambledWord
        viewModel.skipWord()

        val state = viewModel.uiState.value
        assertEquals(0, state.score)
        assertEquals(2, state.currentWordCount)
        assertNotEquals(firstWord, state.currentScrambledWord)
    }

    private fun unscramble(scrambledWord: String): String =
        allWords.single { word -> word.length == scrambledWord.length }
}
