package my.android.travelapp.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
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

@Composable
fun AboutDeveloperPage(navController: NavController) {
    val appBarElevation = 2.dp


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        CustomTopBar(
            onClick = { navController.navigateUp() },
            title = "About Developer",
            appBarElevation = appBarElevation
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            ProfilePictureAD(avatar = R.drawable.laptop_man)
            Spacer(modifier = Modifier.height(20.dp))
            DetailsAD(
                name = "Ayush Sinha",
                email = "ayushsinha.kumar19@gmail.com"
            )
        }
    }
}

@Composable
fun ProfilePictureAD(avatar: Int) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = avatar),
            contentDescription = "Profile picture",
            modifier = Modifier
                .clip(CircleShape)
                .size(120.dp)
                .border(
                    BorderStroke(4.dp, Color(0xFF6A62B7)),
                    CircleShape
                )
        )
    }
}

@Composable
fun DetailsAD(name: String, email: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = email, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = stringResource(R.string.developer_description),
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Connect with me:",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        SocialMediaLinks()
    }
}

@Composable
fun SocialMediaLinks() {
    val uriHandler = LocalUriHandler.current
    val isDarkTheme = isSystemInDarkTheme()

    Column(
        modifier = Modifier.padding(bottom = 30.dp, top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SocialMediaIcon(if (isDarkTheme)R.drawable.github_dark_mode else R.drawable.github_logo) {
                uriHandler.openUri("https://github.com/ayush19sinha") }

            SocialMediaIcon(R.drawable.linkedin_icon) {
                uriHandler.openUri("https://www.linkedin.com/in/ayush19sinha/") }

            SocialMediaIcon(R.drawable.x_icon) {
                uriHandler.openUri("https://www.linkedin.com/in/ayush19sinha/") }

            SocialMediaIcon(R.drawable.ic_instagram) {
                uriHandler.openUri("https://www.linkedin.com/in/ayush19sinha/") }
        }
    }
}

@Composable
fun SocialMediaIcon(resourceId: Int, onClick:()-> Unit) {
    AsyncImage(
        model = resourceId,
        contentDescription = null,
        modifier = Modifier
            .size(46.dp)
            .padding(4.dp)
            .clickable(onClick = onClick)
    )
}

@Preview
@Composable
fun AboutDeveloperPagePreview() {
    val navController = rememberNavController()
    AboutDeveloperPage(navController = navController)
}
