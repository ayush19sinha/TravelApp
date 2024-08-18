package my.android.travelapp.ui.details

import FavoritesViewModel
import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import my.android.travelapp.R
import my.android.travelapp.data.DetailData
import my.android.travelapp.viewModels.PaymentViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.DetailsPage(navController: NavController,
                viewModel: FavoritesViewModel,
                detailData: DetailData,
                                      animatedVisibilityScope: AnimatedVisibilityScope
) {
    Box(modifier = Modifier.fillMaxSize()) {

        ImageLayer(detailData, "",
            viewModel = viewModel,
            onClick = { navController.navigateUp() })

        AboutLayer(
            detailData = detailData,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxHeight(0.7f)
        )
    }
}

@Composable
fun ImageLayer(detailData: DetailData,
               title:String,
               onClick: () -> Unit,
               viewModel: FavoritesViewModel) {

    val isFavorite by viewModel.isFavorite(detailData).collectAsState(initial = false)
    val icon = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder
    val tint = if (isFavorite) Color.Red else Color.Gray

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize(.33f)
    ) {
        Image(
            painter = painterResource(id = detailData.img),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(modifier = Modifier
            .padding(top = 40.dp, start = 20.dp, end = 20.dp)) {

            Row (Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){

                Box(modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(Color(0x80F7F7F9))
                    .padding(4.dp),
                    contentAlignment = Alignment.Center) {

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable(onClick = onClick),
                        tint = Color.Black
                    )
                }

                Text(text = title, fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF271E7A)
                    )

                Box(modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(Color(0x80F7F7F9))
                    .padding(4.dp),
                    contentAlignment = Alignment.Center) {

                    Icon(
                        imageVector = icon ,
                        contentDescription = null,
                        tint = tint,
                        modifier = Modifier
                            .size(14.dp)
                            .clickable { viewModel.toggleFavorite(detailData) }
                    )
                }
            }
        }
    }
}



@Composable
fun AboutLayer(
    detailData: DetailData,
    modifier: Modifier = Modifier) {

//    var days by remember { mutableStateOf(5) }
    val cost by remember { mutableIntStateOf( detailData.price) }

    Box(
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    topStart = 30.dp,
                    topEnd = 30.dp
                )
            )
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) {
            Row (Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){


                Text(
                    text = stringResource(id = detailData.title),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 10.dp)
                )
                RatingRow(rating = detailData.rating, ratingCount = detailData.ratingCount)

            }
            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = null,
                    modifier = Modifier
                        .size(16.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = stringResource(id = detailData.location),
                    fontSize = 14.sp
                )

            }
            Spacer(modifier = Modifier.height(28.dp))
            SmallIconRow()





            DescriptionSection(
                description = stringResource(id = detailData.desc),
                cost = cost,
                paymentViewModel = PaymentViewModel(),

            )
        }
    }
}



@Composable
fun RatingRow(rating: String, ratingCount: String) {
    val isDarkTheme = isSystemInDarkTheme()
    Row(modifier = Modifier.padding(top = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(5) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color(0xFFFFAB00),
                modifier = Modifier.size(14.dp)
            )
        }
        Text(
            text = rating,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 4.dp)
        )
            Text(text = ratingCount,
                color = if (isDarkTheme) Color(0xFFF7F7F9) else Color(0xFF867878),
                textAlign = TextAlign.Center,
                fontSize =  14.sp,
                modifier = Modifier
                    .padding(start = 4.dp))
    }
}



//@Composable
//fun DaysSelector(days: Int, onDaysChange: (Int) -> Unit) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = 30.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Box(
//            modifier = Modifier
//                .size(28.dp, 36.dp)
//                .clickable {
//                    onDaysChange(
//                        (days - 1)
//                            .coerceAtLeast(0)
//                    )
//                }
//                .background(Color(0xFF897CFF))
//                .clip(RoundedCornerShape(50.dp)),
//            contentAlignment = Alignment.Center
//        ) {
//            Box(
//                modifier = Modifier
//                    .background(MaterialTheme.colorScheme.background)
//                    .size(12.dp, 2.dp)
//            )
//        }
//        Box(
//            modifier = Modifier
//                .background(MaterialTheme.colorScheme.onPrimary)
//                .size(46.dp, 34.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(text = "$days", modifier = Modifier.padding(horizontal = 8.dp))
//        }
//        Box(
//            modifier = Modifier
//                .size(28.dp, 36.dp)
//                .clickable {
//                    onDaysChange(
//                        (days + 1)
//                            .coerceAtMost(30)
//                    )
//                }
//                .background(Color(0xFF897CFF))
//                .clip(RoundedCornerShape(20.dp)),
//            contentAlignment = Alignment.Center
//        ) {
//            Icon(
//                imageVector = Icons.Default.Add,
//                contentDescription = null,
//                tint = MaterialTheme.colorScheme.background
//            )
//        }
//        Icon(
//            painter = painterResource(id = R.drawable.ic_time),
//            contentDescription = null,
//            Modifier.padding(start = 26.dp)
//        )
//        Text(text = "$days Days", Modifier.padding(start = 8.dp), fontSize = 18.sp)
//    }
//}


@Composable
fun SmallIconRow(){
    val images = listOf(
        R.drawable.mount5,
        R.drawable.mount2,
        R.drawable.mount6,
        R.drawable.mountain_copy
    )

    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        images.forEach { img ->
            SmallIconImage(img)
        }

        Box(
            Modifier.size(60.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = R.drawable.mount_extra,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { }
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "+16", color = Color.White)
            }
    }
}
}

@Composable
fun SmallIconImage(img: Int ){
    AsyncImage(model = img  , contentDescription = null,
        Modifier
            .size(60.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { },
        contentScale = ContentScale.Crop)
}

@Composable
fun DescriptionSection(description: String,
                       cost: Int,
                       paymentViewModel: PaymentViewModel,
) {

    val context = LocalContext.current

    Column {

        Text(
            text = "About Destination",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 35.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(60.dp)) {

        Text(
            text = description,
            modifier = Modifier.padding(top = 18.dp),
            fontSize = 16.sp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "₹$cost",
                fontSize = 28.sp,
                color = Color(0xFF6A62B7),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "/Person",
                fontSize = 18.sp,
                color = Color(0xFF6A62B7),
                modifier = Modifier.padding(start = 4.dp)
            )
            Button(
                onClick = {
                    val activity = context as? Activity
                    if (cost== 0) Toast.makeText(context, "You can't book this package for 0 days",
                        Toast.LENGTH_SHORT).show()

                    else activity?.let {
                            paymentViewModel
                                .startPayment(it, cost)

                    }

                },
                colors = ButtonDefaults
                    .buttonColors(containerColor = Color(0xFF6A62B7)),
                modifier = Modifier.padding(start = 70.dp)

            ) {
                Text(text = "Book Now", color = Color.White,
                    textAlign = TextAlign.Center)
            }
        }
    }}
}

//fun calculateCost(detailData: DetailData, days: Int): Int {
//    val initialCost = detailData.price * 5
//    return if (days > 5) {
//        initialCost + ((days - 5) * 500)
//    } else {
//        detailData.price * days
//    }
//}













//@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_UNDEFINED, showBackground = true)
//@Preview
//@Composable
//fun DetailsPagePreview() {
//    val navController = rememberNavController()
//    val viewModel = FavoritesViewModel()
//    val data = DetailData(
//        img = R.drawable.mount1,
//        title = R.string.mount_fuji,
//        rating = "5",
//        "(1235)",
//        desc = R.string.mount_fuji_desc,
//        location = R.string.mount_fuji_location,
//        price = 1000,
//        "Asia"
//    )
//    DetailsPage(navController = navController,
//        detailData = data,
//        viewModel = viewModel,
//    )
//}