package my.android.travelapp.ui.profile

import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import my.android.travelapp.R
import my.android.travelapp.navigation.Routes
import my.android.travelapp.viewModels.AuthState
import my.android.travelapp.viewModels.AuthViewModel

@Composable
fun Profile(navController: NavController, authViewModel: AuthViewModel) {

    val context = LocalContext.current
    val authState = authViewModel.authState.observeAsState()
    val userData by authViewModel.userData.observeAsState()


    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> {
                navController.navigate(Routes.login) { popUpTo(0) }
                Toast.makeText(context, "Logged out Successfully", Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()

    ) {
        CustomTopBar(
            onClick = { navController.navigate(Routes.home){popUpTo(0)} },
            appBarElevation = 2.dp,
            title = "Profile",
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            ProfilePicture(avatar = R.drawable.avatar)
            Spacer(modifier = Modifier.height(20.dp))
            userData?.let { user ->
                Details(name = user.name ?: "N/A", email = user.email ?: "N/A")
            }
            EditProfileButton { navController.navigate(Routes.editprofile) }
            Spacer(modifier = Modifier.height(40.dp))
            Menus(navController, authViewModel )
            Spacer(modifier = Modifier.height(120.dp))
            EndInformation()
        }

        BackHandler {
            navController.navigate("home") {
                popUpTo("home") { inclusive = true }
                launchSingleTop = true
            }
        }
    }
}

@Composable
fun ProfilePicture(avatar: Int) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = avatar),
            contentDescription = "Profile picture",
            modifier = Modifier
                .clip(CircleShape)
                .size(80.dp)
        )
    }
}


@Composable
fun Details(name: String, email: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = email, fontSize = 16.sp)
    }
}

@Composable
fun EditProfileButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A62B7)),
        modifier = Modifier.padding(top = 20.dp)
    ) {
        Text(text = "Edit Profile", color = Color.White)
    }
}

@Composable
fun Menus(navController: NavController,
          authViewModel: AuthViewModel) {
    Column {
        MenuRow(
            icon = Icons.Default.Person,
            text = "About Developer",
            onClick = { navController.navigate(Routes.aboutDeveloper) }
        )
        MenuRow(
            icon = Icons.Default.Info,
            text = "My Bookings",
            onClick = { navController.navigate(Routes.mybooking) }
        )
        MenuRow(
            icon = Icons.Default.Settings,
            text = "Settings",
            onClick = { navController.navigate(Routes.setting) }
        )
        MenuRow(
            iconRes = R.drawable.ic_logout,
            text = "Logout",
            textColor = Color(0xFFDD3636),
            onClick = {
                authViewModel.logout()
            }
        )
    }
}

@Composable
fun MenuRow(icon: ImageVector? = null,
            iconRes: Int? = null, text: String,
            textColor: Color = MaterialTheme.colorScheme.onBackground, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 30.dp)
            .clickable { onClick() }
    ) {
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = Color(0xFF8080E2)
                )
            }
            iconRes?.let {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    tint = Color(0xFF8080E2)
                )
            }
        }
        Text(
            text = text,
            fontSize = 16.sp,
            color = textColor,
            modifier = Modifier
                .padding(start = 22.dp)
                .weight(1f)
        )
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.onPrimary)) {
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
        }
    }
}

@Composable
fun EndInformation() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Joined: 11 August 2024", fontSize = 16.sp)
    }
}

@Preview
@Composable
fun ProfilePreview() {
    val navController = rememberNavController()
    val authViewModel = AuthViewModel()

    Profile(navController = navController, authViewModel)
}
