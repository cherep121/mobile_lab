package com.example.artspace_lab6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.artspace_lab6.data.artworkAt

private val GalleryColors = lightColorScheme(
    primary = Color(0xFF385A64),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFBCEBF4),
    onPrimaryContainer = Color(0xFF001F25),
    background = Color(0xFFF8FAF8),
    surface = Color(0xFFFFFBFF),
    onSurface = Color(0xFF191C1D),
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = android.graphics.Color.TRANSPARENT,
                darkScrim = android.graphics.Color.TRANSPARENT,
            ),
        )
        setContent {
            MaterialTheme(colorScheme = GalleryColors) {
                Surface(modifier = Modifier.fillMaxSize()) { ArtSpaceRoute() }
            }
        }
    }
}

@Composable
fun ArtSpaceRoute(
    modifier: Modifier = Modifier,
    viewModel: ArtSpaceViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ArtSpaceScreen(
        artworkIndex = uiState.artworkIndex,
        onPrevious = viewModel::showPreviousArtwork,
        onNext = viewModel::showNextArtwork,
        modifier = modifier,
    )
}

@Composable
fun ArtSpaceScreen(
    artworkIndex: Int,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val artwork = artworkAt(artworkIndex)

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding(),
    ) {
        if (maxWidth >= 700.dp) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalArrangement = Arrangement.spacedBy(36.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ArtworkImage(
                    artwork = artwork,
                    modifier = Modifier.weight(1.15f).fillMaxHeight(),
                )
                Column(
                    modifier = Modifier.weight(0.85f),
                    verticalArrangement = Arrangement.Center,
                ) {
                    GalleryHeader()
                    Spacer(modifier = Modifier.height(28.dp))
                    ArtworkDetails(artwork)
                    Spacer(modifier = Modifier.height(32.dp))
                    NavigationButtons(
                        onPrevious = onPrevious,
                        onNext = onNext,
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                GalleryHeader()
                Spacer(modifier = Modifier.height(14.dp))
                ArtworkImage(
                    artwork = artwork,
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(18.dp))
                ArtworkDetails(artwork)
                Spacer(modifier = Modifier.height(18.dp))
                NavigationButtons(
                    onPrevious = onPrevious,
                    onNext = onNext,
                )
            }
        }
    }
}

@Composable
private fun GalleryHeader() {
    Text(
        text = stringResource(R.string.art_space),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary,
    )
}

@Composable
private fun ArtworkImage(artwork: Artwork, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Image(
            painter = painterResource(artwork.imageResourceId),
            contentDescription = stringResource(artwork.titleResourceId),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun ArtworkDetails(artwork: Artwork) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(artwork.titleResourceId),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Text(
            text = stringResource(artwork.descriptionResourceId),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp),
        )
    }
}

@Composable
private fun NavigationButtons(
    onPrevious: () -> Unit,
    onNext: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Button(onClick = onPrevious, modifier = Modifier.weight(1f)) {
            Text(stringResource(R.string.previous))
        }
        Button(onClick = onNext, modifier = Modifier.weight(1f)) {
            Text(stringResource(R.string.next))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ArtSpacePreview() {
    MaterialTheme(colorScheme = GalleryColors) {
        ArtSpaceScreen(
            artworkIndex = 0,
            onPrevious = {},
            onNext = {},
        )
    }
}
