package my.android.travelapp.ui.profile

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import my.android.travelapp.data.DetailData
import my.android.travelapp.navigation.Routes

@Composable
fun BookingPage(navController: NavController) {

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


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        CustomTopBar(
            onClick = { navController.navigateUp() },
            title = "My Bookings",
            appBarElevation = 2.dp
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            BookingContent(navController = navController)


        }
    }
}

@Composable
fun BookingContent(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = R.drawable.empty_favourite_screen,
            contentDescription = null,
            modifier = Modifier.size(534.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "No Previous Booking",
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.empty_booking_page),
            color = Color(0xFF667085),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        BookTripsButton(navController)
    }
//    LazyRow(contentPadding = PaddingValues(10.dp)) {items(10){items->
//        BookingItem(detailData = items)
//    }
//    }

}


@Composable
fun BookingItem(detailData: DetailData){
    Card(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(24.dp))
        .height(100.dp)
        .clickable { },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
        elevation = CardDefaults.cardElevation(10.dp)
        ){
            Row (
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically){
                Image(painter = painterResource(id = detailData.img),
                    contentDescription =null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(26.dp))
                        .size(80.dp))
                Spacer(modifier = Modifier.width(20.dp))
                Column(Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween){
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.DateRange, contentDescription = null, Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "24 July 2024")
                    }
                    Text(text = stringResource(id = detailData.title),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold)
                    Row(Modifier.padding(bottom = 8.dp)) {
                        Icon(imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            Modifier
                                .size(16.dp)
                                .offset(y = 4.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = stringResource(id = detailData.location))

                    }
                }
                Spacer(modifier = Modifier.width(100.dp))
                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
            }

    }
}

@Composable
fun BookTripsButton(navController: NavController) {
    Button(
        onClick = { navController.navigate(Routes.home) },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A62B7))
    ) {
        Text(text = "Book Trips", color = Color.White)
    }
}



@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES,)
@Preview(showSystemUi = true)
@Composable
fun BookingPagePreview() {
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
    val navController = rememberNavController()
    Surface {
        BookingPage(navController = navController )
    }

}
