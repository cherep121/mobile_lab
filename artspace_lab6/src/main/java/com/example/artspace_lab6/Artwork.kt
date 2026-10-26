package com.example.artspace_lab6

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Artwork(
    @param:DrawableRes val imageResourceId: Int,
    @param:StringRes val titleResourceId: Int,
    @param:StringRes val descriptionResourceId: Int,
)
