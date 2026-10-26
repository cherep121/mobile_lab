package com.example.dessertclicker_lab6

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import com.example.dessertclicker_lab6.data.DessertRepository
import com.example.dessertclicker_lab6.model.Dessert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class DessertClickerUiState(
    val revenue: Int = 0,
    val dessertsSold: Int = 0,
    @param:DrawableRes val currentDessertImageId: Int,
    val currentDessertPrice: Int,
)

class DessertClickerViewModel : ViewModel() {
    private val desserts = DessertRepository.desserts
    private val firstDessert = desserts.first()

    private val _uiState = MutableStateFlow(
        DessertClickerUiState(
            currentDessertImageId = firstDessert.imageId,
            currentDessertPrice = firstDessert.price,
        ),
    )
    val uiState: StateFlow<DessertClickerUiState> = _uiState.asStateFlow()

    fun sellDessert() {
        _uiState.update { state ->
            val updatedSold = state.dessertsSold + 1
            val dessertToShow = determineDessertToShow(desserts, updatedSold)
            state.copy(
                revenue = state.revenue + state.currentDessertPrice,
                dessertsSold = updatedSold,
                currentDessertImageId = dessertToShow.imageId,
                currentDessertPrice = dessertToShow.price,
            )
        }
    }
}

internal fun determineDessertToShow(desserts: List<Dessert>, dessertsSold: Int): Dessert =
    desserts.last { dessert -> dessertsSold >= dessert.startProductionAmount }
