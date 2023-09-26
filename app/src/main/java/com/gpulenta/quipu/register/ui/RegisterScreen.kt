package com.gpulenta.quipu.register.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gpulenta.quipu.R
import com.gpulenta.quipu.register.data.UserData
import com.gpulenta.quipu.register.ui.RegisterViewModel

@Composable
fun RegisterScreen(viewModel: RegisterViewModel)  {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    Image(painter = painterResource(id = R.drawable.ic_shape), contentDescription = "shape",
        modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_register),
            contentDescription = "Header",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        TextField(
            value = username,
            onValueChange = { username = it },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color(0xFF636262),
                backgroundColor = Color(0xFFDEDDDD),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            placeholder = { Text(text = "Username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color(0xFF636262),
                backgroundColor = Color(0xFFDEDDDD),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text(text = "Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color(0xFF636262),
                backgroundColor = Color(0xFFDEDDDD),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        TextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text(text = "Name") },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color(0xFF636262),
                backgroundColor = Color(0xFFDEDDDD),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        TextField(
            value = lastname,
            onValueChange = { lastname = it },
            placeholder = { Text(text = "Lastname") },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color(0xFF636262),
                backgroundColor = Color(0xFFDEDDDD),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        TextField(
            value = address,
            onValueChange = { address = it },
            placeholder = { Text(text = "Address") },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color(0xFF636262),
                backgroundColor = Color(0xFFDEDDDD),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        TextField(
            value = phone,
            onValueChange = { phone = it },
            placeholder = { Text(text = "Phone") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color(0xFF636262),
                backgroundColor = Color(0xFFDEDDDD),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        // Función para validar un correo electrónico
        fun isValidEmail(email: String): Boolean {
            val emailRegex = Regex("^[A-Za2-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
            return emailRegex.matches(email)
        }

        // Función para validar un número de teléfono con 9 dígitos
        fun isValidPhoneNumber(phone: String): Boolean {
            val phoneRegex = Regex("^\\d{9}$")
            return phoneRegex.matches(phone)
        }

        Button(
            onClick = {
                val userData = UserData(
                    username = username,
                    password = password,
                    name = name,
                    lastname = lastname,
                    address = address,
                    email = email,
                    phone = phone
                )
                viewModel.registerUser(userData)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF22C55E),
                disabledBackgroundColor = Color(0xFF3B82F6),
                contentColor = Color.White,
                disabledContentColor = Color.White
            ),
            enabled = isValidEmail(email) && isValidPhoneNumber(phone) && password.length >= 6 && username.length >= 6 && name.length >= 3 && lastname.length >= 3 && address.length >= 10
        ) {
            Text(
                text = "Register",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }
}
