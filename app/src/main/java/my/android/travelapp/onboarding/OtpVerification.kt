package my.android.travelapp.onboarding

import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import my.android.travelapp.navigation.Routes
import my.android.travelapp.ui.profile.CustomTopBar

@Composable
fun OtpVerification(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            CustomTopBar(
                { navController.navigate(Routes.forgetPassword){popUpTo(0)} },
                title = "",
                appBarElevation = 0.dp
            )
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(20.dp)) {
            Spacer(modifier = Modifier.height(40.dp))
            OtpVerificationScreen(navController = navController)
        }
        }
    }
}

@Composable
fun OtpVerificationScreen(navController: NavController) {

    val context = LocalContext.current
    var otp1 by remember { mutableStateOf("") }
    var otp2 by remember { mutableStateOf("") }
    var otp3 by remember { mutableStateOf("") }
    var otp4 by remember { mutableStateOf("") }
    val maxChar = 1

    var countdown by remember { mutableIntStateOf(30) }
    val coroutineScope = rememberCoroutineScope()

    fun startCountdown() {
        coroutineScope.launch(Dispatchers.IO) {
            for (i in 30 downTo 0) {
                withContext(Dispatchers.Main) {
                    countdown = i
                }
                delay(1000L)
            }
        }
    }

    LaunchedEffect(Unit) {
        startCountdown()
    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "OTP Verification", fontWeight = FontWeight.SemiBold, fontSize = 26.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Please check your emails for the OTP",
            textAlign = TextAlign.Center, color = Color(0xFF7D848D)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Box(Modifier.fillMaxWidth()) {
            Text(text = "OTP Code", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 10.dp)
                .fillMaxWidth()
        ) {
            TextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = otp1,
                onValueChange = { newOTP -> if (newOTP.length <= maxChar) otp1 = newOTP },
                modifier = Modifier
                    .size(66.dp)
                    .padding(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFF7F7F9),
                    focusedContainerColor = Color(0xFFF7F7F9)
                ),
                singleLine = true
            )

            TextField(
                value = otp2,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = { newotp -> if (newotp.length <= maxChar) otp2 = newotp },
                modifier = Modifier
                    .size(66.dp)
                    .padding(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFF7F7F9),
                    focusedContainerColor = Color(0xFFF7F7F9)
                ),
                singleLine = true
            )

            TextField(
                value = otp3,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = { newotp -> if (newotp.length <= maxChar) otp3 = newotp },
                modifier = Modifier
                    .size(66.dp)
                    .padding(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFF7F7F9),
                    focusedContainerColor = Color(0xFFF7F7F9)
                ),
                singleLine = true
            )

            TextField(
                value = otp4,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = { newotp -> if (newotp.length <= maxChar) otp4 = newotp },
                modifier = Modifier
                    .size(66.dp)
                    .padding(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFF7F7F9),
                    focusedContainerColor = Color(0xFFF7F7F9)
                ),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {Toast.makeText(context,"OTP verified Successfully", Toast.LENGTH_SHORT).show()
                navController.navigate(Routes.home) {
                    popUpTo(0)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A62B7))
        ) {
            Text(text = "Verify")
        }
        Spacer(modifier = Modifier.height(10.dp))
Row (Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.End){
    Text(text = "Resend Code in")
    Spacer(modifier = Modifier.width(6.dp))
    Text(text = "$countdown seconds")
        }
        Spacer(modifier = Modifier.height(20.dp))
        if (countdown == 0){
        Button(onClick = { startCountdown()
                         Toast.makeText(context,"OTP resent on your email",
                             Toast.LENGTH_SHORT).show()},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A62B7))
        ) {
            Text(text = "Resend OTP")
        }
    }
    }
}

@Preview
@Composable
fun OtpScreenPreview() {
    val navController = rememberNavController()
    OtpVerification(navController)
}
