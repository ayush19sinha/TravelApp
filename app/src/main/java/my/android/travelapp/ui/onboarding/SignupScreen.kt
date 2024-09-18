package my.android.travelapp.ui.onboarding

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
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
fun SignupScreen(navController: NavController, authViewModel: AuthViewModel){


    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        ) {

        Column(Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            CustomTopBar(
                onClick = { navController.navigate(Routes.login){
                popUpTo(0)
            } }, title = "",
                appBarElevation = 0.dp)
            Column(Modifier.fillMaxSize().padding(20.dp)) {
                Spacer(modifier = Modifier.height(40.dp))
                SignupElements(navController, authViewModel)

            }


        }


    }
}

@Composable
fun SignupElements(navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated -> navController.navigate(Routes.home)
            {popUpTo(0)}

            is AuthState.Error -> Toast.makeText(context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()

            else -> Unit
        }
    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Text(text = "Sign up now", fontWeight = FontWeight.SemiBold, fontSize = 26.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Please fill the details to create an account", color = Color(0xFF7D848D))
        Spacer(modifier = Modifier.height(40.dp))
        TextField(
            value = name,
            onValueChange = { newName -> name = newName },
            modifier = Modifier
                .clip(RoundedCornerShape(14.dp))
                .padding(top = 20.dp)
                .fillMaxWidth(),
            label = { Text(text = "Name") },
            colors = OutlinedTextFieldDefaults
                .colors(unfocusedContainerColor = Color(0xFFF7F7F9)),

            )
        Spacer(modifier = Modifier.height(10.dp))


        TextField(
            value = email,
            onValueChange = { newEmail -> email = newEmail },
            modifier = Modifier
                .clip(RoundedCornerShape(14.dp))
                .padding(top = 20.dp)
                .fillMaxWidth(),
            label = { Text(text = "Email") },
            colors = OutlinedTextFieldDefaults
                .colors(unfocusedContainerColor = Color(0xFFF7F7F9)),

            )
        Spacer(modifier = Modifier.height(10.dp))

        TextField(

            value = password,
            visualTransformation = if (passwordVisible)
                VisualTransformation.None else PasswordVisualTransformation(),
            onValueChange = { newPassword -> password = newPassword },
            label = { Text(text = "Password") },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {

                }
                Icon(
                    painter = if (passwordVisible)
                        painterResource(id = R.drawable.ic_visibility)
                    else painterResource(id = R.drawable.ic_visibility_off),
                    contentDescription = null
                )
            },
            modifier = Modifier
                .clip(RoundedCornerShape(14.dp))
                .padding(top = 20.dp)
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults
                .colors(unfocusedContainerColor = Color(0xFFF7F7F9)),

            )
        Spacer(modifier = Modifier.height(10.dp))

        Box (Modifier.fillMaxWidth()){
            Text(
                text = "Password must be of at least 6 characters",
                fontSize = 14.sp

                )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                authViewModel.signUp(email, password, name)

    },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor =
                Color(0xFF6A62B7)
            )
        ) {
            Text(text = "Sign up")
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Text(text = "Already have an account?")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Sign in", color = Color.Blue,
                modifier = Modifier.clickable { navController.navigate(Routes.login) })
        }
        Spacer(modifier = Modifier.height(40.dp))

        Text(text = "Or Connect")


        Column(
            Modifier
                .fillMaxSize()
                .padding(bottom = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

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
fun SignupScreenPreview() {
    val navController = rememberNavController()
    val authViewModel = AuthViewModel()

    SignupScreen(navController = navController, authViewModel)
}
