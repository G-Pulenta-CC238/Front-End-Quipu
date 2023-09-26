package com.gpulenta.quipu.login.ui

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpulenta.quipu.shared.RetrofitClient
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _navigateToDashboard = MutableLiveData<Boolean>()
    val navigateToDashboard: LiveData<Boolean> get() = _navigateToDashboard

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)
    }

    private fun isValidPassword(password: String): Boolean = password.length > 6

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun saveUserIdToSharedPreferences(context: Context, userId: String) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("YourPreferencesName", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("USER_ID", userId)
        editor.apply()
    }
    private fun getUserIdFromSharedPreferences(context: Context): String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("YourPreferencesName", Context.MODE_PRIVATE)
        return sharedPreferences.getString("USER_ID", null)
    }
    private fun printUserIdFromSharedPreferences(context: Context) {
        val storedUserId = getUserIdFromSharedPreferences(context)
        if (storedUserId != null) {
            Log.d("StoredUserId", "User ID from SharedPreferences: $storedUserId")
        } else {
            Log.d("StoredUserId", "No user ID stored.")
        }
    }
    fun onLoginSelected(context: Context) {
        val userEmail = email.value
        val userPassword = password.value
        if (userEmail != null && userPassword != null) {
            viewModelScope.launch {
                try {
                    val response = RetrofitClient.apiService.login(userEmail, userPassword)
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        responseBody?.let {
                            val userId = it.id.toString()
                            saveUserIdToSharedPreferences(context, userId)
                            val message = it.message
                            printUserIdFromSharedPreferences(context)
                        }
                        _navigateToDashboard.value = true
                    } else {
                        _showDialog.value = true
                    }
                } catch (e: Exception) {
                    Log.e("Error", "Error: ${e.message}", e)
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }
    fun onDialogDismissed() {
        _showDialog.value = false
    }
}
