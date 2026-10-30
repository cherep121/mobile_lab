package com.example.mycity_lab7.ui

import androidx.lifecycle.ViewModel
import com.example.mycity_lab7.data.PlaceRepository
import com.example.mycity_lab7.model.Place
import com.example.mycity_lab7.model.PlaceCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class MyCityUiState(
    val selectedCategory: PlaceCategory? = null,
    val selectedPlace: Place? = null,
)

class MyCityViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MyCityUiState())
    val uiState: StateFlow<MyCityUiState> = _uiState.asStateFlow()

    fun selectCategory(category: PlaceCategory) {
        _uiState.update {
            MyCityUiState(
                selectedCategory = category,
                selectedPlace = PlaceRepository.placesFor(category).firstOrNull(),
            )
        }
    }

    fun selectPlace(place: Place) {
        _uiState.update { it.copy(selectedPlace = place) }
    }
}
