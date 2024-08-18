package my.android.travelapp.util

import FavoritesViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import my.android.travelapp.R
import my.android.travelapp.data.DetailData
import my.android.travelapp.data.details
import my.android.travelapp.navigation.Routes

@Composable
fun DisplayPlaces(detailData: DetailData,
                  isSmall : Boolean = false,
                  favoritesViewModel: FavoritesViewModel,
                  navController: NavController) {

    val isFavorite by favoritesViewModel.isFavorite(detailData).collectAsState(initial = false)
    val icon = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder
    val tint = if (isFavorite) Color.Red else Color.Gray
    Box(
        modifier = Modifier
            .height(if (isSmall) 190.dp else 253.dp)
            .width(if (isSmall) 270.dp else 335.dp)
            .clip(RoundedCornerShape(19.dp))
            .clickable {
                navController.navigate(
                    Routes.detail.replace(
                        "{placeId}",
                        details
                            .indexOf(detailData)
                            .toString()
                    )
                )

            }
    ) {
        AsyncImage(
            model = detailData.img,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color.Black.copy(alpha = 0.5f))
                .height(81.dp)
                .width(335.dp)
                .clip(RoundedCornerShape(20.dp))
        ) {
            Column {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp, top = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = detailData.title),
                        fontSize = if (isSmall) 14.sp else 20.sp,
                        color = Color.White,
                        modifier = Modifier.padding(start = 21.dp)
                    )
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = tint,
                            modifier = Modifier
                                .size(14.dp)
                                .clickable { favoritesViewModel.toggleFavorite(detailData) }
                        )
                    }
                }
                Column {
                    Row (Modifier.padding(start = 22.dp),
                        verticalAlignment = Alignment.CenterVertically){
                    Icon(imageVector = Icons.Default.LocationOn ,
                        contentDescription = null,
                        tint = Color.White, modifier = Modifier.size(12.dp) )
                    Text(text = stringResource(id = detailData.location),
                        color = Color.White, fontSize = if (isSmall) 11.sp else 12.sp) }
                    Spacer(modifier = Modifier.height(2.dp))

                Row(modifier = Modifier.padding(start = 22.dp)) {

                    repeat(if (isSmall) 1 else 5) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFAB00),
                            modifier = Modifier.size(if (isSmall) 12.dp else 16.dp)
                        )
                    }
                    Text(
                        text = detailData.rating,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = if (isSmall) 11.sp else 14.sp,
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .offset(y = if (isSmall) (-3.5).dp else 0.dp)
                    )
                }
            }
            }
        }
    }
}


@Composable
fun SmallDisplayPlaces(detailData: DetailData,
                  isSmall : Boolean = false,
                  favoritesViewModel: FavoritesViewModel,
                  navController: NavController) {

    val isFavorite by favoritesViewModel.isFavorite(detailData).collectAsState(initial = false)
    val icon = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder
    val tint = if (isFavorite) Color.Red else Color.Gray
    Column(
        modifier = Modifier
            .width(if (isSmall) 270.dp else 335.dp)
            .clip(RoundedCornerShape(19.dp))
            .background(Color.White)
            .clickable {
                navController.navigate(
                    Routes.detail.replace(
                        "{placeId}",
                        details
                            .indexOf(detailData)
                            .toString()
                    )
                )

            }
    ) {
        Image(
            painter = painterResource(id = detailData.img),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(if (isSmall) 190.dp else 253.dp)
                .width(if (isSmall) 270.dp else 335.dp)
        )
        Box(
            modifier = Modifier
                .height(81.dp)
                .width(335.dp)
                .clip(RoundedCornerShape(20.dp))
        ) {
            Column {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp, top = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = detailData.title),
                        fontSize = if (isSmall) 14.sp else 20.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(start = 21.dp)
                    )
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = tint,
                            modifier = Modifier
                                .size(14.dp)
                                .clickable { favoritesViewModel.toggleFavorite(detailData) }
                        )
                    }
                }
                Column {
                    Row (Modifier.padding(start = 22.dp),
                        verticalAlignment = Alignment.CenterVertically){
                        Icon(imageVector = Icons.Default.LocationOn ,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                             modifier = Modifier.size(12.dp) )
                        Text(text = stringResource(id = detailData.location),
                            color = MaterialTheme.colorScheme.onBackground,
                             fontSize = if (isSmall) 11.sp else 12.sp) }
                    Spacer(modifier = Modifier.height(4.dp))

                    Row(modifier = Modifier.padding(start = 22.dp)) {

                        repeat(if (isSmall) 1 else 5) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFFFAB00),
                                modifier = Modifier.size(if (isSmall) 12.dp else 16.dp)
                            )
                        }
                        Text(
                            text = detailData.rating,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center,
                            fontSize = if (isSmall) 11.sp else 14.sp,
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .offset(y = if (isSmall) (-3.5).dp else 0.dp)
                        )
                    }
                }
            }
        }
    }
}



@Preview
@Composable
private fun DisplayPicturesPreview() {
    val  navController = rememberNavController()
    val data = DetailData(
        img = R.drawable.mount1,
        title = R.string.mount_fuji,
        rating = "5",
        "(1235)",
        desc = R.string.mount_fuji_desc,
        location = R.string.mount_fuji_location,
        price = 1000,
        "Asia"
    )
    val favoritesViewModel = FavoritesViewModel()
    DisplayPlaces(detailData =data , favoritesViewModel =favoritesViewModel , navController = navController)

}




@Preview
@Composable
private fun SmallDisplayPicturesPreview() {
    val  navController = rememberNavController()
    val data = DetailData(
        img = R.drawable.mount1,
        title = R.string.mount_fuji,
        rating = "5",
        "(1235)",
        desc = R.string.mount_fuji_desc,
        location = R.string.mount_fuji_location,
        price = 1000,
        "Asia"
    )
    val favoritesViewModel = FavoritesViewModel()
    SmallDisplayPlaces(detailData =data , favoritesViewModel =favoritesViewModel , navController = navController)

}