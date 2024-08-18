package my.android.travelapp.ui.profile

import FavoritesViewModel
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import my.android.travelapp.R
import my.android.travelapp.navigation.Routes
import my.android.travelapp.ui.home.BottomAppBar
import my.android.travelapp.util.DisplayPlaces


@Composable
fun FavouriteScreen(navController: NavController, favoritesViewModel: FavoritesViewModel){
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
    val favoritePlaces by favoritesViewModel.favoritePlaces.collectAsState()

Box(modifier = Modifier.fillMaxSize()){
    Column(
        Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        ) {
        CustomTopBar(
            { navController.navigateUp() },
            title = "Your Favourite Places", appBarElevation =appBarElevation
        )

        if (favoritePlaces.isEmpty()) {

            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = R.drawable.empty_favourite_screen,
                    contentDescription = null,
                    Modifier.size(534.dp)
                )
                Text(
                    text = "No Favourite Destinations",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = stringResource(R.string.empty_favourite_text),
                    color = Color(0xFF667085),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(18.dp))
                Button(
                    onClick = { navController.navigate(Routes.home) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A62B7))
                ) {
                    Text(text = "Add Favourites", color = Color.White)
                }
            }
        } else {
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
                items(favoritePlaces.toList()) { place ->
                    DisplayPlaces(
                        detailData = place,
                        isSmall = true,
                        navController = navController,
                        favoritesViewModel = favoritesViewModel
                    )
                }
            }
        }
    }
        BottomAppBar(navController, 1)

    }
}


@Preview
@Composable
fun FavouriteScreenPreview(){
    val navController = rememberNavController()
    val  favoritesViewModel = FavoritesViewModel()

    FavouriteScreen(navController, favoritesViewModel = favoritesViewModel )
}