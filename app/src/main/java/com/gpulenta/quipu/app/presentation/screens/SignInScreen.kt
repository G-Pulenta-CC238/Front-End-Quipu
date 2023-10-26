package com.gpulenta.quipu.presentation.screens

import android.content.Context
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gpulenta.quipu.R
import com.gpulenta.quipu.presentation.viewmodels.SignInViewModel
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(viewModel: SignInViewModel, navController: NavHostController) {

    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)

    BackGround(Modifier.fillMaxSize())
    if (isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        Box(
            Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
                .padding(24.dp)
                .clip(RoundedCornerShape(25.dp))
                .background(Color(0f, 0f, 0f, 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            MainSignIn(
                Modifier
                    .align(Alignment.Center)
                    .padding(24.dp), viewModel, navController, LocalContext.current
            )
        }
    }
}

@Composable
fun BackGround(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.background_quipu),
        contentDescription = "Background",
        contentScale = ContentScale.FillBounds,
        modifier = modifier
    )
}

@Composable
fun HeaderImage(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.sign_in_quipu),
        contentDescription = "Header",
        modifier = modifier
    )
}

@Composable
fun UsernameField(username: String, onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = username, onValueChange = { onTextFieldChanged(it) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "UsernameIcon"
            )
        },
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(
                text = "USERNAME",
                fontSize = 14.sp
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
        maxLines = 1
    )
}

@Composable
fun PasswordField(password: String, onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = password, onValueChange = { onTextFieldChanged(it) },
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "keyIcon") },
        label = {
            Text(
                text = "PASSWORD",
                fontSize = 14.sp
            )
        },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        visualTransformation = PasswordVisualTransformation(),
    )
}

@Composable
fun SignInButton(loginEnable: Boolean, onLoginSelected: () -> Unit) {
    Button(
        onClick = { onLoginSelected() },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF5E17EB),
            contentColor = Color.White
        ),
        enabled = loginEnable
    ) {
        Text(
            text = "SIGN IN",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SignUpButton(navController: NavHostController) {
    Button(
        onClick = { navController.navigate("SignUp") },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF00BF63),
            contentColor = Color.White
        ),
    ) {
        Text(
            text = "SING UP",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun MainSignIn(
    modifier: Modifier,
    viewModel: SignInViewModel,
    navController: NavHostController,
    context: Context
) {

    val username: String by viewModel.username.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val loginEnable: Boolean by viewModel.loginEnable.observeAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier) {
        HeaderImage(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.padding(8.dp))
        UsernameField(username) { viewModel.onLoginChanged(it, password) }
        Spacer(modifier = Modifier.padding(4.dp))
        PasswordField(password) { viewModel.onLoginChanged(username, it) }
        Spacer(modifier = Modifier.padding(16.dp))
        SignInButton(loginEnable) {
            coroutineScope.launch {
                viewModel.onLoginSelected(navController, context)
            }
        }
        Spacer(modifier = Modifier.padding(4.dp))
        SignUpButton(navController)
    }
}