package com.gpulenta.quipu.app.presentation.viewmodels

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
}




