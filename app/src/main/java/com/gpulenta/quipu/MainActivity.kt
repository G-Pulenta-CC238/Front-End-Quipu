package com.gpulenta.quipu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.gpulenta.quipu.dashboard.ui.DashboardScreen
import com.gpulenta.quipu.login.ui.LoginScreen
import com.gpulenta.quipu.login.ui.LoginViewModel
import com.gpulenta.quipu.register.ui.RegisterScreen
import com.gpulenta.quipu.register.ui.RegisterViewModel
import com.gpulenta.quipu.ui.theme.QuipuTheme

class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuipuTheme {
                val navigateToDashboard = loginViewModel.navigateToDashboard.observeAsState(false)
                val navigateToRegister = loginViewModel.navigateToRegister.observeAsState(false)
                val registrationSuccessful = registerViewModel.registrationSuccessful.observeAsState(false)

                if (registrationSuccessful.value) {
                    // Si el registro es exitoso, muestra la pantalla de inicio de sesión
                    LoginScreen(loginViewModel)
                } else {
                    when {
                        navigateToDashboard.value -> {
                            //DashboardScreen()
                            DashboardScreen(loginViewModel, this)
                        }
                        navigateToRegister.value -> {
                            RegisterScreen(registerViewModel)
                        }
                        else -> {
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = MaterialTheme.colorScheme.background
                            ) {
                                LoginScreen(loginViewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}

