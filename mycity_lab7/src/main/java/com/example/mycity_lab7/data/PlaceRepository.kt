package com.example.mycity_lab7.data

import com.example.mycity_lab7.R
import com.example.mycity_lab7.model.Place
import com.example.mycity_lab7.model.PlaceCategory

object PlaceRepository {
    val places = listOf(
        Place(
            id = "park_lesnaya_skazka",
            category = PlaceCategory.PARKS,
            name = "Парк «Лесная сказка»",
            address = "м-н Лесная Поляна, Весенний проспект",
            description = "Абсолютно новый ландшафт с искусственным водоёмом, насыпными холмами и ручьём с мостиком.",
            imageRes = R.drawable.park_lesnaya_skazka,
        ),
        Place(
            id = "park_kuzbasskiy",
            category = PlaceCategory.PARKS,
            name = "Парк Кузбасский",
            address = "проспект Химиков, дом 32",
            description = "Парк Кузбасский — зона отдыха, заложенная в 2008 году как подарок городу к 65-летию Кемеровской области.",
            imageRes = R.drawable.park_kuzbasskiy,
        ),
        Place(
            id = "park_voloshinoy",
            category = PlaceCategory.PARKS,
            name = "Комсомольский парк им. Веры Волошиной",
            address = "улица Гагарина, 124а",
            description = "Городской парк культуры и отдыха, заложенный в конце 1960-х годов.",
            imageRes = R.drawable.park_voloshinoy,
        ),
    )

    fun placesFor(category: PlaceCategory): List<Place> =
        places.filter { it.category == category }

    fun placeById(id: String): Place? = places.firstOrNull { it.id == id }
}
