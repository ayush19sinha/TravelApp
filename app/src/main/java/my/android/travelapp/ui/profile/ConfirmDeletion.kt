package my.android.travelapp.ui.profile



import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
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
fun DeletionScreen(navController: NavController, authViewModel: AuthViewModel){
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Unauthenticated ->
            {
                Toast.makeText(context, "Account deleted successfully.", Toast.LENGTH_SHORT).show()
                navController.navigate(Routes.login){popUpTo(0)}}
            is AuthState.Error -> {
                val errorMessage = (authState as AuthState.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            else -> Unit
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {
        Column(Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            CustomTopBar(
                onClick = { navController.navigateUp() }, title = "",
                appBarElevation = 0.dp)
            Column(Modifier.fillMaxSize()
                .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {


                Spacer(modifier = Modifier.height(40.dp))
                DeletionElements(navController, authViewModel = authViewModel)
            }}
    }
}

@Composable
fun DeletionElements(navController: NavController, authViewModel: AuthViewModel){
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState = authViewModel.authState.observeAsState()


    Column(Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {


        Text(text = "Confirm Account Deletion", fontWeight = FontWeight.SemiBold, fontSize = 26.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Please enter your account details to confirm account deletion.", color = Color(0xFF7D848D), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(60.dp))

        TextField(
            value = email,
            onValueChange = { newEmail -> email = newEmail },
            modifier = Modifier
                .clip(RoundedCornerShape(14.dp))
                .fillMaxWidth(),
            label = {Text(text = "Email")},
            colors = OutlinedTextFieldDefaults
                .colors(unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary),

            )
        Spacer(modifier = Modifier.height(30.dp))

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
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults
                .colors(unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary),

            )
        Spacer(modifier = Modifier.height(30.dp))

        Button(onClick = {authViewModel.deleteAccount(email = email, password = password)
        },
            enabled = authState.value != AuthState.Loading,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF700F0F))) {
            Text(text = "Delete Account", color = Color.White)
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Text(text = "Don't want to delete account?")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Go back", color = Color.Blue,
                modifier = Modifier
                    .clickable { navController.navigate(Routes.home) })
        }
        Spacer(modifier = Modifier.height(40.dp))


    }
}


@Preview
@Composable
fun DeletionScreenPreview(){
    val navController = rememberNavController()
    val authViewModel = AuthViewModel()
    DeletionScreen(navController = navController, authViewModel = authViewModel)
}