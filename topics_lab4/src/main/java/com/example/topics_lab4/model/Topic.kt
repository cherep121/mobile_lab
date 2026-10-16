package com.example.topics_lab4.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Topic(
    @param:StringRes val nameResourceId: Int,
    val availableCourses: Int,
    @param:DrawableRes val imageResourceId: Int
)
