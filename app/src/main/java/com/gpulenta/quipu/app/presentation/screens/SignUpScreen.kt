package com.gpulenta.quipu.presentation.screens

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gpulenta.quipu.R
import com.gpulenta.quipu.domain.model.request.RegisterRequest
import com.gpulenta.quipu.presentation.viewmodels.SignUpViewModel


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun SignUpScreen(viewModel: SignUpViewModel, navController: NavHostController) {
    val context = LocalContext.current
    val username by viewModel.username.observeAsState(initial = "")
    val email by viewModel.email.observeAsState(initial = "")
    val password by viewModel.password.observeAsState(initial = "")
    val firstName by viewModel.firstName.observeAsState(initial = "")
    val lastName by viewModel.lastName.observeAsState(initial = "")
    val phone by viewModel.phone.observeAsState(initial = "")
    val address by viewModel.address.observeAsState(initial = "")
    val registrationData = remember { mutableStateOf(RegisterRequest("", "", "", "", "", "", "")) }
    Image(
        painter = painterResource(id = R.drawable.background_quipu), contentDescription = "shape",
        modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .padding(24.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(Color(0f, 0f, 0f, 0.1f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp)
        ) {
            HeaderImageSignUp(Modifier.align(Alignment.CenterHorizontally))
            OutlinedTextField(
                value = username,
                onValueChange = { newValue ->
                    viewModel.onUsernameChanged(newValue)
                    registrationData.value = registrationData.value.copy(username = newValue)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "UsernameIcon"
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
                    viewModel.onRegisterSelected(context, registrationData.value, navController)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5E17EB),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(),
            ) {
                Text("SIGN UP")
            }
        }
    }
}

@Composable
fun HeaderImageSignUp(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.signup_quipu),
        contentDescription = "Header",
        modifier = modifier
    )
}