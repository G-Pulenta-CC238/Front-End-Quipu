package com.gpulenta.quipu.dashboard.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DashboardScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Contenido del dashboard
        Text(text = "Dashboard Screen")
    }
}