package my.android.travelapp.ui.home

import FavoritesViewModel
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import my.android.travelapp.R
import my.android.travelapp.data.details
import my.android.travelapp.navigation.Routes
import my.android.travelapp.util.DisplayPlaces
import my.android.travelapp.util.ThemeSwitcher
import my.android.travelapp.viewModels.AuthState
import my.android.travelapp.viewModels.AuthViewModel
import my.android.travelapp.viewModels.ThemeViewModel

@Composable
fun Home(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel,
    authViewModel: AuthViewModel,
    themeViewModel: ThemeViewModel,
) {
    val authState by authViewModel.authState.observeAsState()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val isDarkTheme by themeViewModel.isDarkTheme.observeAsState(false)

    LaunchedEffect(authState) {
        if (authState is AuthState.Unauthenticated) {
            navController.navigate(Routes.login) { popUpTo(0) }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    navController = navController,
                    isDarkTheme = isDarkTheme,
                    themeViewModel = themeViewModel,
                    onItemClick = {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
            }
        }
    ) {

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                AppBar(navController,
                    onClick = {
                        scope.launch {
                        drawerState.open()
            }}) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                MenuItems()
                Spacer(modifier = Modifier.height(30.dp))
                PlacesCarousel(navController, favoritesViewModel)
                Spacer(modifier = Modifier.height(12.dp))
                SectionHeader("Recommended") { navController.navigate(Routes.recommended) }
                RecommendedSection(navController, favoritesViewModel)
                Spacer(modifier = Modifier.height(12.dp))
                SectionHeader("Most Visited") { navController.navigate(Routes.mostvisited) }
                MostVisitedSection(navController, favoritesViewModel)
                Spacer(modifier = Modifier.height(100.dp))
            }
            BottomAppBar(navController, 0)
        }
    }
}
}

@Composable
fun SectionHeader(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "View All",
            color = Color.Black,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(end = 18.dp)
                .clickable(onClick = onClick)
        )
    }
}

@Composable
fun RecommendedSection(navController: NavController, favoritesViewModel: FavoritesViewModel) {
    val recommended = details.subList(3,8)
    LazyRow(
        contentPadding = PaddingValues(
            start = 12.dp,
            end = 12.dp,
            top = 16.dp,
            bottom = 16.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(recommended) { place ->
            DisplayPlaces(
                detailData = place,
                isSmall = true,
                navController = navController,
                favoritesViewModel = favoritesViewModel,
            )
        }
    }
}

@Composable
fun MostVisitedSection(navController: NavController, favoritesViewModel: FavoritesViewModel) {
    val mostVisited = details.takeLast(6)
    LazyRow(
        contentPadding = PaddingValues(
            start = 12.dp,
            end = 12.dp,
            top = 16.dp,
            bottom = 16.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(mostVisited) { place ->
            DisplayPlaces(
                detailData = place,
                isSmall = true,
                navController = navController,
                favoritesViewModel = favoritesViewModel
            )
        }
    }
}

@Composable
fun AppBar(navController: NavController, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 20.dp, end = 20.dp, top = 50.dp, bottom = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = null,
            modifier = Modifier.clickable(onClick = onClick)
        )
        Text(text = "Discover", fontSize = 27.sp)
        AsyncImage(
            model =  R.drawable.avatar,
            contentDescription = null,
            modifier = Modifier
                .size(44.dp)
                .clickable { navController.navigate(Routes.profile) }
                .clip(RoundedCornerShape(30.dp))
        )
    }
}

@Preview
@Composable
fun MenuItems() {
    val isDark = isSystemInDarkTheme()
    val color = if (isDark) Color(0xFF40307A) else Color(0xFF554592)
    var selectedIndex by remember { mutableIntStateOf(0) }
    LazyRow {
        items(menuList) { item ->
            val index = menuList.indexOf(item)
            Text(
                text = item,
                color = if (index == selectedIndex) color else MaterialTheme.colorScheme.onBackground,
                fontWeight = if (index == selectedIndex) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier
                    .padding(20.dp)
                    .clickable { selectedIndex = index }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlacesCarousel(navController: NavController, favoritesViewModel: FavoritesViewModel) {
    val carouselItems = details.take(7)
    val pagerState = rememberPagerState(pageCount = { carouselItems.size })
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
        while (true) {
            delay(2600)
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % pagerState.pageCount)
        }
    }
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 20.dp),
            pageSpacing = 10.dp
        ) { page ->
            DisplayPlaces(
                detailData = carouselItems[page],
                navController = navController,
                favoritesViewModel = favoritesViewModel
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            HorizontalPagerIndicator(pagerState = pagerState)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerIndicator(pagerState: PagerState) {
    val carouselItems = details.take(7)
    Row {
        carouselItems.forEachIndexed { index, _ ->
            val color = if (pagerState.currentPage == index) Color(0xFF897CFF) else Color(0xFFB9B1FF)
            val width by animateDpAsState(
                targetValue = if (pagerState.currentPage == index) 16.dp else 6.dp,
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                label = ""
            )
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .height(4.dp)
                    .width(width)
            )
        }
    }
}

@Composable
fun BottomAppBar(navController: NavController, selectedIndex: Int) {
    var selectedMenu by remember { mutableIntStateOf(selectedIndex) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 28.dp)
            .zIndex(1f)
            .background(Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .width(380.dp)
                .height(80.dp)
                .clip(RoundedCornerShape(44.dp))
                .background(MaterialTheme.colorScheme.background)
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp, start = 10.dp, top = 10.dp)
                .clickable { },
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomAppBarItem(
                icon = if (selectedMenu == 0) Icons.Filled.Home else Icons.Outlined.Home,
                isSelected = selectedMenu == 0,
                size = if (selectedMenu == 0) 28.dp else 26.dp,
                onClick = {
                    selectedMenu = 0
                    navController.navigate(Routes.home)
                }
            )
            BottomAppBarItem(
                icon = if (selectedMenu == 1) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                isSelected = selectedMenu == 1,
                size = if (selectedMenu == 1) 28.dp else 26.dp,
                onClick = {
                    selectedMenu = 1
                    navController.navigate(Routes.favourite)
                }
            )
            BottomAppBarItem(
                icon = if (selectedMenu == 2) Icons.Filled.Search else Icons.Outlined.Search,
                isSelected = selectedMenu == 2,
                size = if (selectedMenu == 2) 28.dp else 26.dp,
                onClick = {
                    selectedMenu = 2
                    navController.navigate(Routes.search)
                }
            )
            BottomAppBarItem(
                icon = if (selectedMenu == 3) Icons.Filled.Person else Icons.Outlined.Person,
                isSelected = selectedMenu == 3,
                size = if (selectedMenu == 3) 28.dp else 26.dp,
                onClick = {
                    selectedMenu = 3
                    navController.navigate(Routes.profile)
                }
            )
        }
    }
}

@Composable
fun BottomAppBarItem(icon: ImageVector, isSelected: Boolean, size: Dp, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isSelected) Color(0xFF6A62B7) else MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .animateContentSize()
                .size(size)
                .clickable(onClick = onClick)
        )
        Box(
            modifier = Modifier
                .height(4.dp)
                .width(24.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(if (isSelected) Color(0xFF6A62B7) else MaterialTheme.colorScheme.background)
        )
    }
}





@Composable
fun DrawerContent(navController: NavController, onItemClick: () -> Unit,
                  isDarkTheme: Boolean, themeViewModel: ThemeViewModel) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        
        Spacer(modifier = Modifier.height(40.dp))
        Image(painter = painterResource(id = R.drawable.airplane),
            contentDescription =null, Modifier.size(100.dp) )
        Text(
            "Travel App",
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "  Version 1.0.0  \n© Ayush Sinha",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(0.54f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))
        Row (
            Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ){
            Row {

                Image(painter = painterResource(id = R.drawable.dark_toogle),
                    contentDescription = null, Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(30.dp))
                Text(text = "Switch theme")
            }
            ThemeSwitcher(
                darkTheme = isDarkTheme,
                size = 30.dp,
                padding = 8.dp,
                onClick = { themeViewModel.toggleTheme() }
            )
        }
        DrawerItem("Home", Icons.Default.Home) {
            navController.navigate(Routes.home)
            onItemClick()
        }
        DrawerItem("Favorites", Icons.Default.Favorite) {
            navController.navigate(Routes.favourite)
            onItemClick()
        }
        DrawerItem("Search", Icons.Default.Search) {
            navController.navigate(Routes.search)
            onItemClick()
        }
        DrawerItem("Profile", Icons.Default.Person) {
            navController.navigate(Routes.profile)
            onItemClick()
        }
        DrawerItem("About Developer",
            iconRes =  R.drawable.ic_developer) {
            navController.navigate(Routes.aboutDeveloper)
            onItemClick()
        }
    }
}

@Composable
fun DrawerItem(title: String, icon: ImageVector? = null, iconRes: Int? =null, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {icon?.let {

        Icon(
            imageVector = it, contentDescription = null)
    }
        iconRes?.let {
        Icon(painter = painterResource(id = it), contentDescription = null, Modifier.size(24.dp))
    }

        Spacer(modifier = Modifier.width(32.dp))
        Text(title)
    }
}





private val menuList = listOf(
    "Popular", "Featured", "Europe", "Asia", "Most Visited"
)

//@Preview
//@Composable
//fun HomePreview() {
//    val navController = rememberNavController()
//    val favoritesViewModel = viewModel<FavoritesViewModel>()
//    val authViewModel = viewModel<AuthViewModel>()
//    val themeViewModel = ThemeViewModel()
//
//    Home(navController, favoritesViewModel, authViewModel, themeViewModel)
//}




