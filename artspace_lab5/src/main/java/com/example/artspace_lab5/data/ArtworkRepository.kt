package com.example.artspace_lab5.data

import com.example.artspace_lab5.Artwork
import com.example.artspace_lab5.R

fun artworkAt(index: Int): Artwork = when (index) {
    0 -> Artwork(
        imageResourceId = R.drawable.toyota_land_cruiser_prado,
        titleResourceId = R.string.toyota_land_cruiser_prado,
        descriptionResourceId = R.string.toyota_land_cruiser_prado_description,
    )
    1 -> Artwork(
        imageResourceId = R.drawable.mazda_rx7,
        titleResourceId = R.string.mazda_rx7,
        descriptionResourceId = R.string.mazda_rx7_description,
    )
    else -> Artwork(
        imageResourceId = R.drawable.ram_1500_rho,
        titleResourceId = R.string.ram_1500_rho,
        descriptionResourceId = R.string.ram_1500_rho_description,
    )
}
