package my.android.travelapp.ui.profile

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import my.android.travelapp.R
import my.android.travelapp.navigation.Routes
import my.android.travelapp.viewModels.AuthViewModel

@Composable
fun EditProfile(navController: NavController,
                authViewModel: AuthViewModel) {

    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTopBar(
            onClick = { navController.navigate(Routes.profile) },
            title = "Edit Profile",
            appBarElevation = 2.dp
        )
        Spacer(modifier = Modifier.height(50.dp))
        ProfilePictureEditable()
        Spacer(modifier = Modifier.height(36.dp))
        VariousFields(
            onClick = {
                navController.navigate(Routes.profile) {
                    popUpTo(Routes.profile) { inclusive = true }
                    launchSingleTop = true
                }
                Toast.makeText(context, "Edits Saved Successfully", Toast.LENGTH_SHORT).show()
            },authViewModel
        )
    }
}

@Composable
fun ProfilePictureEditable() {

    val context = LocalContext.current
    val defaultUri = Uri.parse("android.resource://${context.packageName}/${R.drawable.avatar}")

    var imageUri by remember {
        mutableStateOf<Uri?>(defaultUri)
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(), onResult = { uri ->
            uri?.let {
                imageUri = it
            }
        }
    )

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.size(80.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            AsyncImage(
                model = imageUri,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                    .size(70.dp)
            )
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF6A62B7))
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_camera),
                    contentDescription = "Camera",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun VariousFields(onClick: () -> Unit, authViewModel: AuthViewModel) {

    val userData by authViewModel.userData.observeAsState()
    val username = userData?.name
    val useremail = userData?.email

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var name by rememberSaveable { mutableStateOf(username) }
        var email by rememberSaveable { mutableStateOf(useremail) }
        var number by rememberSaveable { mutableStateOf("9876543210") }
        var password by rememberSaveable { mutableStateOf("123456") }
        var passwordVisible by rememberSaveable { mutableStateOf(false) }

        val radioOptions = listOf("Male", "Female")
        val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

        name?.let { ProfileTextField(label = "Full Name", value = it, onValueChange = { name = it }) }
        Spacer(modifier = Modifier.height(16.dp))
        email?.let { ProfileTextField(label = "Email", value = it, onValueChange = { email = it }, topPadding = 14.dp) }
        GenderSelection(radioOptions = radioOptions, selectedOption = selectedOption, onOptionSelected = onOptionSelected)
        ProfileTextField(label = "Phone", value = number, onValueChange = { number = it }, topPadding = 18.dp)
        Spacer(modifier = Modifier.height(16.dp))
        ProfileTextField(
            label = "Password",
            value = password,
            onValueChange = { password = it },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(id = if (passwordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off),
                        contentDescription = null
                    )
                }
            },
            topPadding = 14.dp,
        )
        SaveButton(onClick)
    }
}

@Composable
fun ProfileTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    topPadding: Dp = 20.dp,
) {
    Column(modifier = Modifier.fillMaxHeight()) {
        Text(text = label, fontWeight = FontWeight.Bold)
        TextField(
            value = value,
            onValueChange = onValueChange,
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon,
            modifier = Modifier.padding(top = topPadding)
                .clip(RoundedCornerShape(14.dp)),
            colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary))
    }
}

@Composable
fun GenderSelection(
    radioOptions: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Text(text = "Gender", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 18.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        radioOptions.forEach { text ->
            Row(
                modifier = Modifier
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) }
                    )
                    .padding(horizontal = 14.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = { onOptionSelected(text) },
                    modifier = Modifier.padding(8.dp)
                )
                Text(text = text, modifier = Modifier.padding(start = 4.dp), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SaveButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A62B7)),
        modifier = Modifier.padding(top = 40.dp)
    ) {
        Text(text = "Save Edits", color = Color.White)
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfilePreview() {
    val navController = rememberNavController()
    val authViewModel = AuthViewModel()
    EditProfile(navController, authViewModel)
}