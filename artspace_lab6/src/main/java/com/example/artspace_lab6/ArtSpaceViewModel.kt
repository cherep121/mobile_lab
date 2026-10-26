package com.example.artspace_lab6

import androidx.lifecycle.ViewModel
import com.example.artspace_lab6.data.ARTWORK_COUNT
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ArtSpaceUiState(val artworkIndex: Int = 0)

class ArtSpaceViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ArtSpaceUiState())
    val uiState: StateFlow<ArtSpaceUiState> = _uiState.asStateFlow()

    fun showPreviousArtwork() {
        _uiState.update { state ->
            state.copy(
                artworkIndex = if (state.artworkIndex == 0) {
                    ARTWORK_COUNT - 1
                } else {
                    state.artworkIndex - 1
                },
            )
        }
    }

    fun showNextArtwork() {
        _uiState.update { state ->
            state.copy(artworkIndex = (state.artworkIndex + 1) % ARTWORK_COUNT)
        }
    }
}
