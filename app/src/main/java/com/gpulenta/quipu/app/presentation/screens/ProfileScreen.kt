package com.gpulenta.quipu.presentation.screens

import android.app.Dialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gpulenta.quipu.R
import com.gpulenta.quipu.domain.model.request.RegisterRequest
import com.gpulenta.quipu.presentation.viewmodels.ProfileViewModel



@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel, navController: NavHostController) {
    var selectedNavItem by remember { mutableStateOf(0) }
    val context = LocalContext.current
    val username by viewModel.username.observeAsState(initial = "")
    val email by viewModel.email.observeAsState(initial = "")
    val password by viewModel.password.observeAsState(initial = "")
    val firstName by viewModel.firstName.observeAsState(initial = "")
    val lastName by viewModel.lastName.observeAsState(initial = "")
    val phone by viewModel.phone.observeAsState(initial = "")
    val address by viewModel.address.observeAsState(initial = "")
    val registrationData = remember { mutableStateOf(RegisterRequest("", "", "", "", "", "", "")) }


    val showUpdateSuccessDialog = viewModel.updateSuccess.value

    if (showUpdateSuccessDialog) {
        AlertDialog(
            onDismissRequest = {
                viewModel.resetUpdateSuccess() // Oculta el diálogo y reinicia el estado
            },
            title = { Text("USER UPDATED") },
            text = { Text("The user has been updated successfully.") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.resetUpdateSuccess()
                    },
                ) {
                    Text("OK")
                }
            }
        )
    }

    val isButtonEnabled = username.length >= 6 && email.length >= 6 &&
            password.length >= 6 && phone.length >= 6 &&
            address.length >= 6


    LaunchedEffect(Unit) {
        viewModel.fetchUserData() // Reemplaza 'userId' con el ID del usuario que deseas cargar
    }

    Image(
        painter = painterResource(id = R.drawable.background_quipu), contentDescription = "shape",
        modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .padding(24.dp)
            .padding(top = 50.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(Color(0f, 0f, 0f, 0.1f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp)
        ) {
            HeaderImageProfile(Modifier.align(Alignment.CenterHorizontally).height(110.dp))
            OutlinedTextField(
                value = username,
                onValueChange = { newValue ->
                    viewModel.onUsernameChanged(newValue)
                    registrationData.value = registrationData.value.copy(username = newValue)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "UserIcon"
                    )
                },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { newValue ->
                    viewModel.onEmailChanged(newValue)
                    registrationData.value = registrationData.value.copy(email = newValue)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "EmailIcon"
                    )
                },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { newValue ->
                    viewModel.onPasswordChanged(newValue)
                    registrationData.value = registrationData.value.copy(password = newValue)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "keyIcon"
                    )
                },
                enabled = false,
                maxLines = 1,
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = firstName,
                onValueChange = { newValue ->
                    viewModel.onFirstNameChanged(newValue)
                    registrationData.value = registrationData.value.copy(firstName = newValue)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Badge,
                        contentDescription = "NameIcon"
                    )
                },
                label = { Text("First Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = lastName,
                onValueChange = { newValue ->
                    viewModel.onLastNameChanged(newValue)
                    registrationData.value = registrationData.value.copy(lastName = newValue)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Badge,
                        contentDescription = "LastNameIcon"
                    )
                },
                label = { Text("Last Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { newValue ->
                    viewModel.onPhoneChanged(newValue)
                    registrationData.value = registrationData.value.copy(phone = newValue)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "PhoneIcon"
                    )
                },
                label = { Text("Phone") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = address,
                onValueChange = { newValue ->
                    viewModel.onAddressChanged(newValue)
                    registrationData.value = registrationData.value.copy(address = newValue)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "HomeIcon"
                    )
                },
                label = { Text("Address") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.updateUserProfile()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5E17EB),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(),
                enabled = isButtonEnabled
            ) {
                Text("UPDATE")
            }
        }
    }
    BottomNavigation(
        modifier = Modifier
            .height(64.dp),
        backgroundColor = Color(0xFF5E17EB),
    ) {
        itemsa.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title, tint = Color.White) },
                label = {
                    Text(
                        text = item.title,
                        color = Color.White,
                        style = TextStyle(fontSize = 12.sp)
                    )
                },
                selected = index == selectedNavItem,
                onClick = {
                    selectedNavItem = index
                    when (item.title) {
                        "Home" -> navController.navigate("Dashboard")
                        "News" -> navController.navigate("New")
                        "Cart" -> navController.navigate("ShoppingCart")
                        "Exit" -> {
                            val sharedPreferences =
                                context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                            with(sharedPreferences.edit()) {
                                clear()
                                apply()
                            }
                            navController.navigate("SignIn")
                        }

                        else -> {}
                    }
                },
            )
        }
    }
}
@Composable
fun HeaderImageProfile(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.profile_quipu),
        contentDescription = "Header",
        modifier = modifier
    )
}

