package com.example.mycity_lab7.model

import androidx.annotation.DrawableRes

enum class PlaceCategory(val route: String, val title: String) {
    RESTAURANTS("restaurants", "Рестораны"),
    PARKS("parks", "Парки"),
    COFFEE_SHOPS("coffee_shops", "Кофейни"),
    CINEMAS("cinemas", "Кинотеатры"),
    SHOPPING_CENTERS("shopping_centers", "Торговые центры"),
}

data class Place(
    val id: String,
    val category: PlaceCategory,
    val name: String,
    val address: String,
    val description: String,
    @param:DrawableRes val imageRes: Int,
)
