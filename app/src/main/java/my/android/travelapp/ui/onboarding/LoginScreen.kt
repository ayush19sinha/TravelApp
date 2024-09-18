package my.android.travelapp.ui.onboarding

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import my.android.travelapp.R
import my.android.travelapp.navigation.Routes
import my.android.travelapp.ui.profile.CustomTopBar
import my.android.travelapp.viewModels.AuthState
import my.android.travelapp.viewModels.AuthViewModel

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel){

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {
        Column(Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            CustomTopBar(
                onClick = { navController.navigate(Routes.onboardingScreen){
                popUpTo(0)
            } }, title = "",
                appBarElevation = 0.dp)
            Column(Modifier.fillMaxSize()
                .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {


            Spacer(modifier = Modifier.height(40.dp))
            LoginElements(navController, authViewModel)
        }}
    }
}

@Composable
fun LoginElements(navController: NavController, authViewModel: AuthViewModel){
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated ->
                navController.navigate(Routes.home){popUpTo(0)}
            else -> Unit
        }
    }

Column(Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally) {


    Text(text = "Sign in now", fontWeight = FontWeight.SemiBold, fontSize = 26.sp)
    Spacer(modifier = Modifier.height(10.dp))
    Text(text = "Please sign in to continue using our app", color = Color(0xFF7D848D))
    Spacer(modifier = Modifier.height(40.dp))

    TextField(
        value = email,
        onValueChange = { newEmail -> email = newEmail },
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .padding(top = 20.dp)
            .fillMaxWidth(),
        label = {Text(text = "Email")},
        colors = OutlinedTextFieldDefaults
            .colors(unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary),

        )
    Spacer(modifier = Modifier.height(10.dp))

    TextField(
        value = password,
        visualTransformation = if (passwordVisible)
            VisualTransformation.None else PasswordVisualTransformation(),
        onValueChange = { newPassword -> password = newPassword },
        label = {Text(text = "Password")},
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                
            }
            Icon(painter = if (passwordVisible)
            painterResource(id = R.drawable.ic_visibility)
        else painterResource(id = R.drawable.ic_visibility_off), contentDescription =null )},
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .padding(top = 20.dp)
            .fillMaxWidth(),
        colors = OutlinedTextFieldDefaults
            .colors(unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary),

        )
    Spacer(modifier = Modifier.height(10.dp))

    Text(text = "Forget Password?", color = Color.Blue,
        modifier = Modifier
            .padding(start = 180.dp)
            .clickable { navController.navigate(Routes.forgetPassword) })

    Spacer(modifier = Modifier.height(20.dp))

    Button(onClick = {authViewModel.login(email, password)

    },
        enabled = authState.value != AuthState.Loading,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor =
        Color(0xFF6A62B7))) {
        Text(text = "Sign in", color = Color.White)
    }

    Spacer(modifier = Modifier.height(20.dp))
    Row {
        Text(text = "Don't have an account?")
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Sign Up", color = Color.Blue,
            modifier = Modifier
                .clickable { navController.navigate(Routes.signup) })
    }
    Spacer(modifier = Modifier.height(40.dp))

    Text(text = "Or Connect")

    Column(
        Modifier
            .fillMaxSize()
            .padding(bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom) {

        Row (Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){

            Image(painter = painterResource(id = R.drawable.ic_fb),
                contentDescription = null,
                Modifier
                    .size(42.dp)
                    .padding(4.dp)
                    .clickable { }
            )

            Image(painter = painterResource(id = R.drawable.ic_google),
                contentDescription = null,
                Modifier
                    .size(40.dp)
                    .padding(4.dp)
                    .clickable { }
            )
            Image(painter = painterResource(id = R.drawable.ic_twitter),
                contentDescription = null,
                Modifier
                    .size(46.dp)
                    .padding(4.dp)
                    .clickable { }
            )
            }
        }
    }
}


@Preview
@Composable
fun LoginScreenPreview(){
    val navController = rememberNavController()
    val authViewModel = AuthViewModel()
    LoginScreen(navController = navController, authViewModel)
}