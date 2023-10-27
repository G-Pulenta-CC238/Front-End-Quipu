package com.gpulenta.quipu.presentation.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.gpulenta.quipu.data.remote.retrofit.RetrofitClient.apiService
import com.gpulenta.quipu.domain.model.request.LoginRequest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException

class SignInViewModel : ViewModel() {

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun onLoginChanged(username: String, password: String) {
        _username.value = username
        _password.value = password
        _loginEnable.value = isValidUsername(username) && isValidPassword(password)
    }

    private fun isValidPassword(password: String): Boolean = password.length >= 6
    private fun isValidUsername(username: String): Boolean = username.length >= 6

    fun onLoginSelected(navController: NavHostController, context: Context) {
        _isLoading.value = true
        val usernameValue = username.value ?: ""
        val passwordValue = password.value ?: ""
        val loginRequest = LoginRequest(usernameValue, passwordValue)
        viewModelScope.launch {
            try {
                val response = apiService.login(loginRequest)
                val id = response.id
                val token = response.token
                val shoppingCartid = apiService.getShoppingCartByUser(id)
                val idShoppingCart = shoppingCartid.id
                Log.d("LoginViewModel", "ShoppingcartId : $idShoppingCart")
                navController.navigate("Dashboard")
                saveCredentialsInSharedPreferences(context, id, idShoppingCart, token)
                printCredentialsFromSharedPreferences(context)
            } catch (e: HttpException) {
                Log.e("Network Error", "Error en la solicitud HTTP: ${e.message}")
            } catch (e: SocketTimeoutException) {
                Log.e("Timeout Error", "Tiempo de espera agotado: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun saveCredentialsInSharedPreferences(
        context: Context,
        id: Long,
        idShoppingCart: Long,
        token: String
    ) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putLong("id", id)
        editor.putLong("idShoppingCart", idShoppingCart)
        editor.putString("token", token)
        editor.apply()
    }

    fun printCredentialsFromSharedPreferences(context: Context) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val id = sharedPreferences.getLong("id", 0)
        val idShoppingCart = sharedPreferences.getLong("idShoppingCart", 0)
        val token = sharedPreferences.getString(
            "token",
            ""
        ) // "" es un valor predeterminado si no se encuentra la clave

        Log.d("Credentials", "ID: $id, ShoppingCartID: $idShoppingCart,  Token: $token")
    }

}

