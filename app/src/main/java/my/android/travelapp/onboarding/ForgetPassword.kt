package my.android.travelapp.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import my.android.travelapp.navigation.Routes
import my.android.travelapp.ui.profile.CustomTopBar
import my.android.travelapp.viewModels.AuthViewModel

@Composable
fun ForgetPassword(navController: NavController){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {


        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            CustomTopBar(
                { navController.navigate(Routes.login){popUpTo(0)} }, title = "",
                appBarElevation = 0.dp)
            Column (
                Modifier
                    .fillMaxSize()
                    .padding(20.dp)){
                Spacer(modifier = Modifier.height(30.dp))

                Spacer(modifier = Modifier.height(40.dp))
                ForgetPasswordScreen(navController)
            }
        }
    }
}


@Composable
fun ForgetPasswordScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    val authViewModel = AuthViewModel()
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Text(text = "Forget Password", fontWeight = FontWeight.SemiBold, fontSize = 26.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Enter your registered email address to reset your password", textAlign = TextAlign.Center,color = Color(0xFF7D848D))
        Spacer(modifier = Modifier.height(40.dp))

        TextField(
            value = email,
            onValueChange = { newEmail -> email = newEmail },
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            label = { Text(text = "Email") },
            colors = OutlinedTextFieldDefaults
                .colors(unfocusedContainerColor = Color(0xFFF7F7F9)),

            )
        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {authViewModel.emailVerification()
                navController.navigate(Routes.otpVerification) {
                    popUpTo(0)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor =
                Color(0xFF6A62B7)
            )
        ) {
            Text(text = "Reset Password")
        }
        Spacer(modifier = Modifier.height(20.dp))


        Button(
            onClick = {
                navController.navigate(Routes.login) {
                    popUpTo(0)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor =
                Color(0xFFF4F4F7)
            )
        ) {
            Text(text = "Sign in instead", color = Color.Black)
        }
    }

}

@Preview
@Composable
fun ForgetPasswordPreview(){
    val navController = rememberNavController()
    ForgetPassword(navController)
}