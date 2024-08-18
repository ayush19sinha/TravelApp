package my.android.travelapp.ui.home

import FavoritesViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import my.android.travelapp.R
import my.android.travelapp.data.details
import my.android.travelapp.navigation.Routes
import my.android.travelapp.ui.profile.CustomTopBar
import my.android.travelapp.util.DisplayPlaces

@Composable
fun SearchPage(navController: NavController, favoritesViewModel: FavoritesViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    Column(
        Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        CustomTopBar(title = "Search", appBarElevation = 2.dp,
            onClick = {navController.navigateUp()})
        Column (
            Modifier
                .padding(horizontal = 10.dp)){


        Spacer(modifier = Modifier.height(20.dp))
        SearchBar(searchQuery = searchQuery, onSearchQueryChange = { searchQuery = it })
            }
        Box(Modifier.padding(top = 10.dp)) {


        SearchResults(searchQuery, favoritesViewModel, navController)
        BottomAppBar(navController, 2)
    }
    }
}


@Composable
fun SearchBar(searchQuery: String, onSearchQueryChange: (String) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange ,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.clickable { onSearchQueryChange ("") }
                    )
                }
            },
            placeholder = {
                Text(text = "Search Places")
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
        )
    }
}

@Composable
fun TopBar2(title: String, navController: NavController, isDark:Boolean) {
    val color = if (isDark) Color(0xFF013E5E) else Color(0xFF61A6CA)
    Box(
        Modifier
            .padding(top = 40.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onPrimary)
                    .padding(4.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null
                )
            }
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp)
            )
            Text(
                text = "Cancel",
                color = color,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(end = 6.dp)
                    .clickable {
                        navController.navigate(Routes.home) {
                            popUpTo(0)
                        }
                    }
            )
        }
    }
}


@Composable
fun SearchResults(searchQuery: String, favoritesViewModel: FavoritesViewModel, navController: NavController) {
    val context = LocalContext.current
    val places = details

    val filteredPlaces = if (searchQuery.isEmpty()) {
        places
    }
    else {
        places.filter { context.getString(it.title).contains(searchQuery, ignoreCase = true) }
    }
    
    if ((places.filter {
            context.getString(it.title).contains(searchQuery, ignoreCase = true)
        }).isEmpty()
    ){
        Column(
            Modifier
                .fillMaxSize()
                .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            Image(painter = painterResource(id = R.drawable.search_empty),
                contentDescription =null,
                )
            Spacer(modifier = Modifier.height(40.dp))
            Text(text = "No result found", fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
            Text(text = "Please try searching for a different place.", textAlign = TextAlign.Center)

        }

    }
        else{

    LazyVerticalGrid(columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(start = 12.dp,
            end = 12.dp, top = 16.dp,
            bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp) ) {items(filteredPlaces){
            place->
        DisplayPlaces(detailData = place, isSmall = true,
            navController = navController, favoritesViewModel = favoritesViewModel)
    }}
    }





}




@Preview
@Composable
fun SearchPagePreview() {
    val navController = rememberNavController()
    val favoritesViewModel = FavoritesViewModel()
    SearchPage(navController, favoritesViewModel)
}
