package com.example.mycity_lab7.data

import com.example.mycity_lab7.R
import com.example.mycity_lab7.model.Place
import com.example.mycity_lab7.model.PlaceCategory

object PlaceRepository {
    val places = listOf(
        Place(
            id = "restaurant_ararat",
            category = PlaceCategory.RESTAURANTS,
            name = "Арарат",
            address = "Ноябрьская улица, 58а",
            description = "Армянская и грузинская кухня.",
            imageRes = R.drawable.restaurant_ararat,
        ),
        Place(
            id = "restaurant_syrovarnya",
            category = PlaceCategory.RESTAURANTS,
            name = "Сыроварня",
            address = "бульвар Строителей, 1б",
            description = "Итальянская кухня.",
            imageRes = R.drawable.restaurant_syrovarnya,
        ),
        Place(
            id = "restaurant_sabai_bar",
            category = PlaceCategory.RESTAURANTS,
            name = "Sabai Bar",
            address = "Советский проспект, 35",
            description = "Паназиатская кухня.",
            imageRes = R.drawable.restaurant_sabai_bar,
        ),
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
        Place(
            id = "coffee_tetya_koritsa",
            category = PlaceCategory.COFFEE_SHOPS,
            name = "Тётя Корица",
            address = "Солнечный бульвар, 32",
            description = "Уютное место, где можно спокойно выпить кофе, перекусить или провести время с детьми.",
            imageRes = R.drawable.coffee_tetya_koritsa,
        ),
        Place(
            id = "coffee_po_kofe",
            category = PlaceCategory.COFFEE_SHOPS,
            name = "По кофе?",
            address = "Красная улица, 16",
            description = "Кофейня-магазин и небольшое семейное дело, основанное Михаилом и Ольгой Погорелко.",
            imageRes = R.drawable.coffee_po_kofe,
        ),
        Place(
            id = "coffee_surf_people",
            category = PlaceCategory.COFFEE_SHOPS,
            name = "Surf Coffee x People",
            address = "улица Демьяна Бедного, 6",
            description = "Интерьер выполнен в мягких тонах с обилием зелени, что создаёт уютную и располагающую обстановку.",
            imageRes = R.drawable.coffee_surf_people,
        ),
        Place(
            id = "cinema_starmax",
            category = PlaceCategory.CINEMAS,
            name = "Starmax Cinema ЛетоСити",
            address = "Московский проспект, 19",
            description = "Один из крупнейших кинокомплексов в Кемерове.",
            imageRes = R.drawable.cinema_starmax,
        ),
        Place(
            id = "cinema_yubileyniy",
            category = PlaceCategory.CINEMAS,
            name = "Юбилейный",
            address = "проспект Ленина, 91",
            description = "Это не просто кинотеатр, а целый комплекс для разного досуга.",
            imageRes = R.drawable.cinema_yubileyniy,
        ),
        Place(
            id = "cinema_kosmos_premier",
            category = PlaceCategory.CINEMAS,
            name = "Космос-Премьер",
            address = "улица 50 лет Октября, 28",
            description = "У этого места долгая история: кинотеатр открыли в 1961 году — изначально как детский специализированный.",
            imageRes = R.drawable.cinema_kosmos_premier,
        ),
    )

    fun placesFor(category: PlaceCategory): List<Place> =
        places.filter { it.category == category }

    fun placeById(id: String): Place? = places.firstOrNull { it.id == id }
}
