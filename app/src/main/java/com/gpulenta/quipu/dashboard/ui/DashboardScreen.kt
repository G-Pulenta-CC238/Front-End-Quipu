package com.gpulenta.quipu.dashboard.ui

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gpulenta.quipu.login.ui.LoginViewModel

@Composable
fun DashboardScreen(viewModel: LoginViewModel, context: Context) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Dashboard Screen")
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.logOut(context)//ESTA VAINA ELIMINA EL USER_ID Y CERRARÁ LA SESIÓN :V
            }
        ) {
            Text(text = "Log Out")
        }
    }
}