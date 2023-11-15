package com.gpulenta.quipu.app.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpulenta.quipu.app.domain.model.response.OfferResponse
import com.gpulenta.quipu.app.domain.model.response.OfferStatusUpdate
import com.gpulenta.quipu.data.remote.retrofit.RetrofitClient
import com.gpulenta.quipu.data.remote.retrofit.RetrofitClient.apiService
import com.gpulenta.quipu.data.remote.service.ApiService
import com.gpulenta.quipu.domain.model.response.CartItemResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OfferViewModel : ViewModel() {
    private val _offers = MutableLiveData<List<OfferResponse>>()
    val offers: LiveData<List<OfferResponse>> = _offers
    private val _myOffers = MutableLiveData<List<OfferResponse>>()
    val myOffers: LiveData<List<OfferResponse>> = _myOffers
    fun fetchOffers() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getOffers()
                if (response.isNotEmpty()) {
                    _offers.postValue(response)
                }
            } catch (e: Exception) {
                // Manejar errores según tus necesidades
            }
        }
    }
    fun updateOfferStatus(offerId: Long, offerStatusUpdate: OfferStatusUpdate) {
        viewModelScope.launch {
            try {
                val response = apiService.updateOfferStatus(offerId, offerStatusUpdate)
                if (response.isSuccessful) {
                    // Manejar la respuesta del servidor
                }
            } catch (e: Exception) {
                // Manejar errores según tus necesidades
            }
        }
    }

    fun deleteOffer(offerId: Long) {
        viewModelScope.launch {
            try {
                val response = apiService.deleteOffer(offerId)
                if (response.isSuccessful) {
                    Log.d("APIResponse", "La oferta fue eliminada correctamente.")
                } else {

                }
            } catch (e: Exception) {

            }
        }
    }

    fun getMyOffers(userId: Long) {
        viewModelScope.launch {
            try {
                val response = apiService.getOffersByUser(userId)

                // Verificar si la respuesta no es nula
                if (response != null) {
                    _myOffers.value = listOf(response) // Convertir el objeto en una lista
                } else {
                    Log.d("APIResponse", "La llamada fue exitosa pero no hay ofertas disponibles.")
                }
            } catch (e: Exception) {
                Log.e("APIError", "Error al obtener las ofertas: ${e.message}")
                // Manejar errores según tus necesidades
            }
        }
    }

}




