package my.android.travelapp.ui.profile

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import my.android.travelapp.R
import my.android.travelapp.navigation.Routes

@Composable
fun SettingPage(navController: NavController) {

    val listState = rememberLazyListState()
    val hasScrolled by remember {
        derivedStateOf {
            listState.firstVisibleItemScrollOffset > 0
        }
    }
    val appBarElevation by animateDpAsState(
        targetValue = if (hasScrolled) 4.dp else 2.dp,
        label = ""
    )
    Scaffold(
        topBar = {
            CustomTopBar(
                onClick = { navController.navigateUp() },
                appBarElevation = appBarElevation,
                title = "Settings"
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top= 30.dp)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                LazyColumn(
                    contentPadding = padding,
                    modifier = Modifier.widthIn(max = 700.dp),
                    state = listState
                ) {
                    item { CategoryItem(title = "Account", icon = Icons.Outlined.AccountCircle, onClick = { /*TODO*/ }) }
                    item { CategoryItem(title = "Payment methods", icon = Icons.Outlined.ShoppingCart, onClick = { /*TODO*/ }) }
                    item { CategoryItem(title = "Privacy", icon = Icons.Outlined.Lock, onClick = { /*TODO*/ }) }
                    item { CategoryItem(title = "Notifications", icon = Icons.Outlined.Notifications, onClick = { /*TODO*/ }) }
                    item { HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp)) }
                    item { CategoryItem(title = "FAQ", icon = Icons.Outlined.Info, onClick = { /*TODO*/ }) }
                    item { CategoryItem(title = "Send Feedback", icon = Icons.Outlined.Email, onClick = { /*TODO*/ }) }
                    item { CategoryItem(title = "See what's new", icon = Icons.Outlined.Star, onClick = { /*TODO*/ }) }
                    item { HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp)) }
                    item { CategoryItem(title = "Legal", icon = Icons.Outlined.Build, onClick = { /*TODO*/ }) }
                    item { CategoryItem(title = "Licenses", iconRes = R.drawable.ic_policy, onClick = { /*TODO*/ }) }
                    item { CategoryItem(title = "Delete Account", icon = Icons.Default.Delete, onClick = {
                        navController.navigate(Routes.confirmDeletion)} , textColor = Color.Red) }
                    item { HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp)) }
                    item { AppVersion(versionText = "Version 1.0.0", copyrights = "© Ayush Sinha", onClick = { /* TODO Add easter egg after 8 times is clicked */ }) }
                }
            }
        }
    )

}
@Composable
fun CategoryItem(title: String, icon: ImageVector? = null,
                 iconRes: Int? = null, onClick: () -> Unit, textColor: Color = MaterialTheme.colorScheme.onBackground ) {
    Surface(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Box(Modifier.background(MaterialTheme.colorScheme.background)) {
                icon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                    )
                }
                iconRes?.let {
                    Icon(
                        painter = painterResource(id = it),
                        contentDescription = null,
                    )
                }
            }
            Text(title, style = MaterialTheme.typography.bodyLarge, color = textColor )
        }
    }
}

@Composable
fun AppVersion(versionText: String, copyrights: String, onClick: () -> Unit) {
    Surface(onClick = onClick) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Box(modifier = Modifier.size(40.dp))
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(versionText, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onBackground.copy(0.54f))
                Text(copyrights, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onBackground.copy(0.54f))
            }
        }
    }
}

@Composable
fun BackIcon() {
    Box(
        modifier = Modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = null,
            modifier = Modifier.size(28.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(onClick: () -> Unit, appBarElevation: Dp, title: String) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = Modifier.shadow(appBarElevation),
        title = {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        },
        navigationIcon = {
            IconButton(onClick = onClick) {
                BackIcon()
            }
        },
        actions = {}
    )
}

@Preview
@Composable
fun SettingPagePreview() {
    val navController = rememberNavController()
    SettingPage(navController )
}
