package com.gpulenta.quipu.login.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gpulenta.quipu.R
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    Image(painter = painterResource(id = R.drawable.ic_shape), contentDescription = "shape",
        modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds
    )
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Login(Modifier.align(Alignment.Center), viewModel, LocalContext.current)
    }
    val showDialog by viewModel.showDialog.observeAsState(initial = false)
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                viewModel.onDialogDismissed()
            },
            buttons = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = {
                            viewModel.onDialogDismissed()
                        }
                    ) {
                        Text("OK")
                    }
                }
            },
            text = {
                Text(
                    "Incorrect Credentials",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        )
    }
}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel, context: Context) {

    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val loginEnable: Boolean by viewModel.loginEnable.observeAsState(initial = false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    if (isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        Column(modifier = modifier) {
            HeaderImage(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(42.dp))
            EmailField(email) { viewModel.onLoginChanged(it, password) }
            Spacer(modifier = Modifier.padding(4.dp))
            PasswordField(password) { viewModel.onLoginChanged(email, it) }
            Spacer(modifier = Modifier.padding(16.dp))
            ForgotPassword(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(16.dp))
            LoginButton(loginEnable) {
                coroutineScope.launch {
                    viewModel.onLoginSelected(context) // Pasa el contexto aquí
                }
            }
            Spacer(modifier = Modifier.padding(4.dp))
            RegisterButton { viewModel.onRegisterSelected() }
        }
    }
}

@Composable
fun LoginButton(loginEnable: Boolean, onLoginSelected: () -> Unit) {
    Button(
        onClick = { onLoginSelected() },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF22C55E),
            disabledBackgroundColor = Color(0xFF3B82F6),
            contentColor = Color.White,
            disabledContentColor = Color.White
        ), enabled = loginEnable
    ) {
        Text(
            text = "Log In",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold)
    }
}

@Composable
fun RegisterButton(onNavigateToRegister: () -> Unit) {

    Button(
        onClick = { onNavigateToRegister() },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF22C55E),
            contentColor = Color.White,
            disabledContentColor = Color.White
        ),
    ) {
        Text(
            text = "Register",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(
        text = "Forgot Password?",
        modifier = modifier.clickable { },
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFFFFFFF)
    )
}

@Composable
fun PasswordField(password: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = password, onValueChange = { onTextFieldChanged(it) },
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "keyIcon") },
        placeholder = { Text(text = "Password") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        visualTransformation = PasswordVisualTransformation(),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF636262),
            backgroundColor = Color(0xFFDEDDDD),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun EmailField(email: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = email, onValueChange = { onTextFieldChanged(it) },
        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon") },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF636262),
            backgroundColor = Color(0xFFDEDDDD),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun HeaderImage(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.logo_login),
        contentDescription = "Header",
        modifier = modifier
    )
}