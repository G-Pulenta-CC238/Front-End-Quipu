package com.gpulenta.quipu.presentation.viewmodels

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.gpulenta.quipu.data.remote.retrofit.RetrofitClient
import com.gpulenta.quipu.data.remote.service.ApiService
import com.gpulenta.quipu.domain.model.request.RegisterRequest
import com.gpulenta.quipu.domain.model.request.ShoppingCart
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignUpViewModel : ViewModel() {
    private val _username = MutableLiveData("")
    val username: LiveData<String> = _username

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _firstName = MutableLiveData("")
    val firstName: LiveData<String> = _firstName

    private val _lastName = MutableLiveData("")
    val lastName: LiveData<String> = _lastName

    private val _phone = MutableLiveData("")
    val phone: LiveData<String> = _phone

    private val _address = MutableLiveData("")
    val address: LiveData<String> = _address

    private val apiService: ApiService = RetrofitClient.apiService

    fun onUsernameChanged(newUsername: String) {
        _username.value = newUsername
    }

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun onFirstNameChanged(newFirstName: String) {
        _firstName.value = newFirstName
    }

    fun onLastNameChanged(newLastName: String) {
        _lastName.value = newLastName
    }

    fun onPhoneChanged(newPhone: String) {
        _phone.value = newPhone
    }

    fun onAddressChanged(newAddress: String) {
        _address.value = newAddress
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun onRegisterSelected(
        context: Context,
        registrationData: RegisterRequest,
        navController: NavHostController
    ) {
        viewModelScope.launch {
            try {
                val response = apiService.register(registrationData)

                val id = response.id
                val token = response.token
                Log.d("RegisterViewModel", "Id: $id")
                createShoppingCart(id)
                navController.navigate("SignIn")

            } catch (e: HttpException) {
                if (e.code() == 403) {
                } else {

                }
            } catch (e: Exception) {

            }
        }
    }

    private fun createShoppingCart(userId: Long) {
        val cartData = ShoppingCart("On", userId)
        viewModelScope.launch {
            try {
                val response = apiService.createShoppingCart(cartData)
                Log.d("RegisterViewModel", "Shopping Cart created")

            } catch (e: Exception) {

                Log.e("RegisterViewModel", "Error creating shopping cart", e)
            }
        }
    }

}