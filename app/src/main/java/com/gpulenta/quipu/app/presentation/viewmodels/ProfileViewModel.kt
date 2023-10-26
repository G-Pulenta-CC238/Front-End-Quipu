package com.gpulenta.quipu.presentation.viewmodels

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpulenta.quipu.data.remote.retrofit.RetrofitClient
import com.gpulenta.quipu.data.remote.service.ApiService
import com.gpulenta.quipu.domain.model.request.RegisterRequest
import com.gpulenta.quipu.domain.model.response.UserProfile
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ProfileViewModel(private val context: Context) : ViewModel() {


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

    private val _updateSuccess = mutableStateOf(false)
    val updateSuccess: State<Boolean> = _updateSuccess

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun fetchUserData() {
        viewModelScope.launch {
            try {
                val sharedPrefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val userId = sharedPrefs.getLong("id", 0L)
                Log.d("ProfileViewModel", "userId: $userId")
                if (userId != -1L) {
                    // El ID del usuario se encontró en las preferencias compartidas, ahora puedes hacer la solicitud con ese ID
                    val userResponse = apiService.getUserData(userId)
                    _username.value = userResponse.username
                    _email.value = userResponse.email
                    _password.value = userResponse.password
                    _firstName.value = userResponse.firstName
                    _lastName.value = userResponse.lastName
                    _phone.value = userResponse.phone
                    _address.value = userResponse.address
                } else {
                }

            } catch (e: HttpException) {
            } catch (e: Exception) {
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun updateUserProfile() {
        viewModelScope.launch {
            try {
                val sharedPrefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val userId = sharedPrefs.getLong("id", 0L)
                val updatedUsername = _username.value ?: ""
                val updatedEmail = _email.value ?: ""
                val updatedPassword = _password.value ?: ""
                val updatedFirstName = _firstName.value ?: ""
                val updatedLastName = _lastName.value ?: ""
                val updatedPhone = _phone.value ?: ""
                val updatedAddress = _address.value ?: ""
                val updatedProfile = UserProfile(
                    updatedUsername,
                    updatedEmail,
                    updatedPassword,
                    updatedFirstName,
                    updatedLastName,
                    updatedPhone,
                    updatedAddress
                )
                apiService.updateUserData(userId, updatedProfile)
                _updateSuccess.value = true
            } catch (e: HttpException) {
            } catch (e: Exception) {
            }
        }
    }
    fun resetUpdateSuccess() {
        _updateSuccess.value = false
    }

}
