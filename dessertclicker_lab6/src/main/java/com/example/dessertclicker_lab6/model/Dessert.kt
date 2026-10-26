package com.example.dessertclicker_lab6.model

import androidx.annotation.DrawableRes

data class Dessert(
    @param:DrawableRes val imageId: Int,
    val price: Int,
    val startProductionAmount: Int,
)
