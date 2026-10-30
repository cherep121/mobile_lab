package com.example.mycity_lab7

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mycity_lab7.data.PlaceRepository
import com.example.mycity_lab7.model.Place
import com.example.mycity_lab7.model.PlaceCategory
import com.example.mycity_lab7.ui.MyCityViewModel
import androidx.compose.foundation.Image

private const val CategoriesRoute = "categories"
private const val PlacesRoute = "places/{category}"
private const val DetailsRoute = "details/{placeId}"

@Composable
fun MyCityApp(
    navController: NavHostController = rememberNavController(),
    viewModel: MyCityViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    NavHost(navController = navController, startDestination = CategoriesRoute) {
        composable(CategoriesRoute) {
            CategoriesScreen(
                onCategorySelected = { category ->
                    viewModel.selectCategory(category)
                    navController.navigate("places/${category.route}")
                },
            )
        }
        composable(PlacesRoute) { backStackEntry ->
            val categoryRoute = backStackEntry.arguments?.getString("category")
            val category = PlaceCategory.entries.firstOrNull { it.route == categoryRoute }
                ?: PlaceCategory.PARKS
            LaunchedEffect(category) { viewModel.selectCategory(category) }

            PlacesScreen(
                category = category,
                selectedPlace = uiState.selectedPlace,
                onBack = navController::navigateUp,
                onPlaceSelected = { place, expanded ->
                    viewModel.selectPlace(place)
                    if (!expanded) navController.navigate("details/${place.id}")
                },
            )
        }
        composable(DetailsRoute) { backStackEntry ->
            val placeId = backStackEntry.arguments?.getString("placeId").orEmpty()
            val place = PlaceRepository.placeById(placeId)
            if (place != null) {
                LaunchedEffect(place.id) { viewModel.selectPlace(place) }
                PlaceDetailsScreen(place = place, onBack = navController::navigateUp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoriesScreen(onCategorySelected: (PlaceCategory) -> Unit) {
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Мой город") }) },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Text(
                    text = "Интересные места Кемерова",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp, bottom = 4.dp),
                )
            }
            items(PlaceCategory.entries) { category ->
                val count = PlaceRepository.placesFor(category).size
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCategorySelected(category) },
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    ),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(category.title, style = MaterialTheme.typography.titleLarge)
                        Text(
                            text = if (count == 0) "скоро" else "$count места",
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                }
            }
            item { Box(Modifier.height(4.dp)) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlacesScreen(
    category: PlaceCategory,
    selectedPlace: Place?,
    onBack: () -> Unit,
    onPlaceSelected: (Place, Boolean) -> Unit,
) {
    val places = PlaceRepository.placesFor(category)
    Scaffold(
        topBar = { MyCityTopBar(title = category.title, onBack = onBack) },
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            val expanded = maxWidth >= 720.dp
            when {
                places.isEmpty() -> EmptyCategory(category.title)
                expanded -> Row(Modifier.fillMaxSize()) {
                    PlaceList(
                        places = places,
                        selectedPlace = selectedPlace,
                        onPlaceSelected = { onPlaceSelected(it, true) },
                        modifier = Modifier.weight(0.42f),
                    )
                    HorizontalDivider(modifier = Modifier.width(1.dp).fillMaxSize())
                    PlaceDetails(
                        place = selectedPlace ?: places.first(),
                        modifier = Modifier.weight(0.58f),
                    )
                }
                else -> PlaceList(
                    places = places,
                    selectedPlace = null,
                    onPlaceSelected = { onPlaceSelected(it, false) },
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
private fun PlaceList(
    places: List<Place>,
    selectedPlace: Place?,
    onPlaceSelected: (Place) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item { Box(Modifier.height(4.dp)) }
        items(places, key = { it.id }) { place ->
            PlaceCard(
                place = place,
                selected = place.id == selectedPlace?.id,
                onClick = { onPlaceSelected(place) },
            )
        }
        item { Box(Modifier.height(4.dp)) }
    }
}

@Composable
private fun PlaceCard(place: Place, selected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            },
        ),
    ) {
        Image(
            painter = painterResource(place.imageRes),
            contentDescription = place.name,
            modifier = Modifier.fillMaxWidth().height(190.dp),
            contentScale = ContentScale.Crop,
        )
        Column(Modifier.padding(16.dp)) {
            Text(place.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(
                text = place.address,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 4.dp),
            )
            Text(
                text = place.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlaceDetailsScreen(place: Place, onBack: () -> Unit) {
    Scaffold(
        topBar = { MyCityTopBar(title = place.name, onBack = onBack) },
    ) { innerPadding ->
        PlaceDetails(
            place = place,
            modifier = Modifier.fillMaxSize().padding(innerPadding),
        )
    }
}

@Composable
private fun PlaceDetails(place: Place, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        item {
            Image(
                painter = painterResource(place.imageRes),
                contentDescription = place.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Fit,
            )
            Text(
                text = place.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 20.dp),
            )
            Text(
                text = place.address,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 8.dp),
            )
            Text(
                text = place.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp, bottom = 24.dp),
            )
        }
    }
}

@Composable
private fun EmptyCategory(categoryTitle: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "Места категории «$categoryTitle» будут добавлены позже",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(24.dp),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyCityTopBar(title: String, onBack: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(title, maxLines = 1, overflow = TextOverflow.Ellipsis)
        },
        navigationIcon = {
            TextButton(onClick = onBack) { Text("Назад") }
        },
    )
}
