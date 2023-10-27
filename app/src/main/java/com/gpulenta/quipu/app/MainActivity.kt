package com.gpulenta.quipu.app

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gpulenta.quipu.presentation.screens.DashboardScreen
import com.gpulenta.quipu.presentation.screens.NewScreen
import com.gpulenta.quipu.presentation.screens.ProfileScreen
import com.gpulenta.quipu.presentation.screens.ShoppingCartScreen
import com.gpulenta.quipu.presentation.screens.SignInScreen
import com.gpulenta.quipu.presentation.screens.SignUpScreen
import com.gpulenta.quipu.presentation.screens.TripScreen
import com.gpulenta.quipu.presentation.ui.theme.QuipuTheme
import com.gpulenta.quipu.presentation.viewmodels.DashboardViewModel
import com.gpulenta.quipu.presentation.viewmodels.NewViewModel
import com.gpulenta.quipu.presentation.viewmodels.ProfileViewModel
import com.gpulenta.quipu.presentation.viewmodels.ShoppingCartViewModel
import com.gpulenta.quipu.presentation.viewmodels.SignInViewModel
import com.gpulenta.quipu.presentation.viewmodels.SignUpViewModel
import com.gpulenta.quipu.presentation.viewmodels.TripViewModel

class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuipuTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    val navController = rememberNavController()
                    val signInViewModel = remember { SignInViewModel() }
                    val signUpViewModel = remember { SignUpViewModel() }
                    val newViewModel = remember { NewViewModel() }
                    val dashboardViewModel = remember { DashboardViewModel() }
                    val profileViewModel = remember { ProfileViewModel(context) }
                    val shoppingCartViewModel = remember { ShoppingCartViewModel(context) }
                    val tripViewModel = remember { TripViewModel() }

                    NavHost(
                        navController = navController,
                        startDestination = "Splash" // Nuevo destino de inicio
                    ) {
                        composable("Splash") {
                            val userId = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                                .getLong("id", 0)
                            Log.d("MainActivity", "userId: $userId")
                            val token = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                                .getString("token", null)
                            val destination = if (userId > 0 && token != null) {
                                "Dashboard"
                            } else {
                                "SignIn"
                            }
                            navController.navigate(destination)
                        }
                        composable("SignIn") {
                            SignInScreen(signInViewModel, navController)
                        }
                        composable("Dashboard") {
                            DashboardScreen(dashboardViewModel, navController)
                        }
                        composable("SignUp") {
                            SignUpScreen(signUpViewModel, navController)
                        }
                        composable("New") {
                            NewScreen(newViewModel, navController)
                        }
                        composable("Profile") {
                            ProfileScreen(profileViewModel, navController)
                        }
                        composable("ShoppingCart") {
                            ShoppingCartScreen(shoppingCartViewModel , navController)
                        }
                        composable("Trip") {
                            TripScreen(tripViewModel , navController)
                        }
                    }
                }
            }
        }
    }
}

