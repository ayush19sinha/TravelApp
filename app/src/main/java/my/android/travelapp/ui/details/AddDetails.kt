package my.android.travelapp.ui.details

//import FavoritesViewModel
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material.icons.outlined.LocationOn
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import my.android.travelapp.R
//import my.android.travelapp.data.DetailData
//import my.android.travelapp.viewModels.PaymentViewModel
//
//@Composable
//fun AddDetails(navController: NavController, detailData: DetailData, viewModel: FavoritesViewModel){
//    Box(
//        Modifier
//            .background(MaterialTheme.colorScheme.background)
//            .fillMaxSize(),
//    ) {
//            ImageLayer(detailData = detailData, viewModel = viewModel, title = "", onClick = {})
//            AboutLayer2(
//                detailData = detailData,
//                modifier = Modifier
//                    .align(Alignment.BottomCenter)
//                    .fillMaxHeight(0.7f)
//            )
//    }
//}
//
//
//@Composable
//fun AboutLayer2(
//    detailData: DetailData,
//    modifier: Modifier = Modifier) {
//
//    var people by remember { mutableStateOf(1) }
//    var cost by remember { mutableStateOf(people * detailData.price) }
//
//    Box(
//        modifier = modifier
//            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
//            .background(Color.White)
//    ) {
//        Column(
//            Modifier
//                .fillMaxSize()
//                .padding(30.dp)
//        ) {
//            Text(
//                text = stringResource(id = detailData.title),
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold,
//                modifier= Modifier.padding(top = 10.dp)
//            )
//
//            Row(
//                modifier = Modifier.padding(top = 8.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Icon(
//                    imageVector = Icons.Outlined.LocationOn,
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(16.dp)
//                )
//                Spacer(modifier = Modifier.width(2.dp))
//                Text(
//                    text = stringResource(id = detailData.location),
//                    fontSize = 14.sp
//                )
//            }
//
//            RatingRow(rating = detailData.rating, ratingCount = detailData.ratingCount)
//
//            PeopleSelector(people = people, onPeopleChange = { newPeople ->
//                people = newPeople
//                cost = people * detailData.price
//            })
//
//            DescriptionSection(
//                description = stringResource(id = detailData.desc),
//                cost = cost,
//                paymentViewModel = PaymentViewModel(),
//            )
//        }
//    }
//}
//
//
//
//@Composable
//fun PeopleSelector( people: Int, onPeopleChange: (Int) -> Unit) {
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = 30.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Box(
//            modifier = Modifier
//                .size(28.dp, 36.dp)
//                .clickable { onPeopleChange((people - 1).coerceAtLeast(0)) }
//                .background(Color(0xFF897CFF))
//                .clip(RoundedCornerShape(50.dp)),
//            contentAlignment = Alignment.Center
//        ) {
//            Box(
//                modifier = Modifier
//                    .background(Color.White)
//                    .size(12.dp, 2.dp)
//            )
//        }
//        Box(
//            modifier = Modifier
//                .background(Color(0xFFF3F3F3))
//                .size(46.dp, 34.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(text = "$people",
//                modifier = Modifier.padding(horizontal = 8.dp))
//        }
//        Box(
//            modifier = Modifier
//                .size(28.dp, 36.dp)
//                .clickable { onPeopleChange((people + 1).coerceAtMost(30)) }
//                .background(Color(0xFF897CFF))
//                .clip(RoundedCornerShape(20.dp)),
//            contentAlignment = Alignment.Center
//        ) {
//            Icon(
//                imageVector = Icons.Default.Add,
//                contentDescription = null,
//                tint = Color.White
//            )
//        }
//        Icon(
//            imageVector = Icons.Default.Person,
//            contentDescription = null,
//            Modifier.padding(start = 26.dp)
//        )
//        Text(text = if (people == 1)"$people Person" else "$people Persons",
//            Modifier.padding(start = 8.dp), fontSize = 18.sp)
//    }
//}
//
//
//
//
//
//
//
//@Preview
//@Composable
//fun AddDetailsPreview(){
//    val viewModel = FavoritesViewModel()
//    val navController = rememberNavController()
//    val data = DetailData(
//        img = R.drawable.mount1,
//        title = R.string.mount_fuji,
//        rating = "5",
//        ratingCount = "(1234)",
//        desc = R.string.mount_fuji_desc,
//        location = R.string.mount_fuji_location,
//        price = 1200,
//        "Asia"
//    )
//AddDetails(navController, data, viewModel = viewModel)
//}