package com.example.artspace_lab5

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Artwork(
    @param:DrawableRes val imageResourceId: Int,
    @param:StringRes val titleResourceId: Int,
    @param:StringRes val descriptionResourceId: Int,
)
