package my.android.travelapp.ui.home

import FavoritesViewModel
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import my.android.travelapp.data.details
import my.android.travelapp.navigation.Routes
import my.android.travelapp.ui.profile.CustomTopBar
import my.android.travelapp.util.DisplayPlaces

@Composable
fun MostVisited(navController: NavController, favoritesViewModel: FavoritesViewModel) {
    val gridState = rememberLazyGridState()
    val hasScrolled by remember {
        derivedStateOf {
            gridState.firstVisibleItemScrollOffset > 0
        }
    }
    val appBarElevation by animateDpAsState(
        targetValue = if (hasScrolled) 4.dp else 2.dp,
        label = ""
    )
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        CustomTopBar(
            onClick = { navController.navigate(Routes.home) },
            title = "Most Visited Places",
            appBarElevation = appBarElevation
        )
        MostVisitedGrid(navController = navController, favoritesViewModel = favoritesViewModel)
    }
}

@Composable
fun MostVisitedGrid(navController: NavController, favoritesViewModel: FavoritesViewModel) {
    val mostVisited = details.takeLast(6)
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = 12.dp,
            end = 12.dp,
            top = 16.dp,
            bottom = 16.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(mostVisited.size) { place ->
            DisplayPlaces(
                detailData = mostVisited[place],
                isSmall = true,
                navController = navController,
                favoritesViewModel = favoritesViewModel
            )
        }
    }
}

@Preview
@Composable
fun MostVisitedPreview() {
    val navController = rememberNavController()
    val favoritesViewModel = FavoritesViewModel()

    MostVisited(navController = navController, favoritesViewModel = favoritesViewModel)
}
