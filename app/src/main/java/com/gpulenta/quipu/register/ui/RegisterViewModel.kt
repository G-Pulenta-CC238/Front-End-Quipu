package com.gpulenta.quipu.register.ui


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gpulenta.quipu.register.data.UserData
import com.gpulenta.quipu.shared.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    private val _registrationSuccessful = MutableLiveData<Boolean>()
    val registrationSuccessful: LiveData<Boolean>
        get() = _registrationSuccessful

    fun registerUser(userData: UserData) {
        val call = RetrofitClient.apiService.createUser(userData)

        call.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    _registrationSuccessful.value = true
                } else {
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                // Maneja errores de la solicitud aquí
                Log.e("RegisterViewModel", "Error: ${t.message}", t)
            }
        })
    }
}