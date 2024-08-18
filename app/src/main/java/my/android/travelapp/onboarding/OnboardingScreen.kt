package my.android.travelapp.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import my.android.travelapp.navigation.Routes
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(navController: NavController) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val items = OnboardingItems.get()
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            AnimatedOnBoardingItem(
                items[page],
                page,
                pagerState.currentPage,
                pagerState.currentPageOffsetFraction
            )
        }

        TopSection(
            onBackClick = {
                if (pagerState.currentPage > 0) scope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                }
            },
            onSkipClick = {
                if (pagerState.currentPage < items.size - 1) scope.launch {
                    pagerState.animateScrollToPage(items.size - 1)
                }
            },
            pagerState = pagerState
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            BottomSection(size = items.size, index = pagerState.currentPage) {
                if (pagerState.currentPage < items.size - 1) scope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
                else {navController.navigate(Routes.login)}
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopSection(onBackClick: () -> Unit, onSkipClick: () -> Unit, pagerState: PagerState) {
    val backButtonVisible by remember(pagerState.currentPage) {
        derivedStateOf { pagerState.currentPage > 0 }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(top = 36.dp)
    ) {
        AnimatedVisibility(
            visible = backButtonVisible,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                    tint = Color.Black,
                    contentDescription = "Back",
                    modifier = Modifier.size(25.dp)
                )
            }
        }
        TextButton(
            onClick = onSkipClick,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Text(text = "Skip", color = Color.Black, fontSize = 16.sp)
        }
    }
}

@Composable
fun AnimatedOnBoardingItem(item: OnboardingItems, page: Int,
                           currentPage: Int, currentPageOffset: Float) {
    val pageOffset = (page - currentPage) + currentPageOffset

    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                alpha = 1f - pageOffset.absoluteValue
                translationX = pageOffset * size.width * 0.5f
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Image(
                painter = painterResource(id = item.img),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(480.dp)
                    .width(420.dp)
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 46.dp,
                            bottomEnd = 46.dp
                        )
                    )
            )

            Spacer(modifier = Modifier.height(50.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 38.dp)
                    .fillMaxWidth()
            )
            {
                Text(
                    text = stringResource(id = item.title),
                    fontSize = 36.sp,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(25.dp))

                Text(
                    text = stringResource(id = item.desc),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun BottomSection(size: Int, index: Int, content: () -> Unit) {
    val text = if (index == 0) "Get Started" else "Continue"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
        ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Indicators(size, index)
            Spacer(modifier = Modifier.height(55.dp))
            Button(onClick = content,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.
                buttonColors(containerColor = Color(0xFF6A62B7))){
                Text(text = text )
            }
            Spacer(modifier = Modifier.height(25.dp))
        }
        
    }
}




@Composable
fun Indicators(size: Int, index: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        repeat(size) {
            Indicator(isSelected = it == index)
        }
    }
}


@Composable
fun Indicator(isSelected: Boolean) {
    val width by animateDpAsState(
        targetValue = if (isSelected) 25.dp else 10.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy), label = ""
    )
    Box(
        modifier = Modifier
            .height(10.dp)
            .width(width)
            .clip(CircleShape)
            .background(
                if (isSelected) Color(0xFF6A62B7)
                else Color(0xFFBEB9F1)
            )
    )
}



@Preview
@Composable
fun OnboardingPreview(){
    val navController= rememberNavController()
    OnboardingScreen(navController= navController)
}


