package com.example.dessertclicker_lab6.data

import com.example.dessertclicker_lab6.R
import com.example.dessertclicker_lab6.model.Dessert

object DessertRepository {
    val desserts = listOf(
        Dessert(R.drawable.cupcake, 5, 0),
        Dessert(R.drawable.donut, 10, 5),
        Dessert(R.drawable.eclair, 15, 20),
        Dessert(R.drawable.froyo, 30, 50),
        Dessert(R.drawable.gingerbread, 50, 100),
        Dessert(R.drawable.honeycomb, 100, 200),
        Dessert(R.drawable.icecreamsandwich, 500, 500),
        Dessert(R.drawable.jellybean, 1_000, 1_000),
        Dessert(R.drawable.kitkat, 2_000, 2_000),
        Dessert(R.drawable.lollipop, 3_000, 4_000),
        Dessert(R.drawable.marshmallow, 4_000, 8_000),
        Dessert(R.drawable.nougat, 5_000, 16_000),
        Dessert(R.drawable.oreo, 6_000, 20_000),
    )
}
