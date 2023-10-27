package com.gpulenta.quipu.presentation.viewmodels

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpulenta.quipu.data.remote.retrofit.RetrofitClient
import com.gpulenta.quipu.data.remote.service.ApiService
import com.gpulenta.quipu.domain.model.request.TripRequest
import com.gpulenta.quipu.domain.model.response.Trip

import kotlinx.coroutines.launch

class TripViewModel : ViewModel() {


    private val apiService: ApiService = RetrofitClient.apiService

    private var _trips = MutableLiveData<List<Trip>>().apply { value = emptyList() }
    val trips: LiveData<List<Trip>> = _trips

    fun createTrip(
        origin: String,
        destination: String,
        date: String,
        userId: Long,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                // Crear un objeto TripRequest con los datos proporcionados
                val tripRequest = TripRequest(
                    origin = origin,
                    destination = destination,
                    date = date,
                    userId = userId
                )

                // Realizar la solicitud POST para crear el viaje
                val response = apiService.createTrip(tripRequest)

                if (response.isSuccessful) {
                    // La solicitud fue exitosa, puedes llamar a la función onSuccess
                    onSuccess()
                } else {
                    // La solicitud no fue exitosa, manejar el error
                    onError("Error al crear el viaje")
                }
            } catch (e: Exception) {
                // Manejar errores de la solicitud
                onError("Error de red: ${e.message}")
            }
        }
    }
    fun loadTripsByUser(userId: Long) {
        viewModelScope.launch {
            try {
                // Realizar una solicitud para obtener los viajes del usuario
                val response = apiService.getTripsByUser(userId)
                if (response.isSuccessful) {
                    val tripList = response.body()
                    if (tripList != null && tripList.isNotEmpty()) {
                        _trips.value = tripList
                    } else {
                        // La lista de viajes está vacía, puedes manejarlo aquí si es necesario
                    }
                } else {

                }
            } catch (e: Exception) {

            }
        }
    }

}
