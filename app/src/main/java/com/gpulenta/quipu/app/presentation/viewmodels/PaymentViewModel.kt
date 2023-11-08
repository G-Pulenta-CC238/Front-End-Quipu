package com.gpulenta.quipu.app.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpulenta.quipu.app.domain.model.response.Payment
import com.gpulenta.quipu.data.remote.retrofit.RetrofitClient.apiService
import kotlinx.coroutines.launch
import retrofit2.Response

class PaymentViewModel() : ViewModel() {

    fun createPayment(payment: Payment) {
        viewModelScope.launch {
            try {
                // Realizar la solicitud POST para crear un nuevo pago
                val response = apiService.createPayment(payment)

                if (response.isSuccessful) {
                    // El pago se creó exitosamente, puedes manejar la respuesta aquí
                } else {
                    // Ocurrió un error en la solicitud, puedes manejarlo según tus necesidades
                }
            } catch (e: Exception) {
                // Manejar errores en la solicitud, por ejemplo, problemas de red
            }
        }
    }
    suspend fun fetchPaymentData(idUser: Long): Response<Payment> {
        return apiService.getPaymentByUser(idUser)
    }
    fun deletePayment(paymentId: Long) {
        viewModelScope.launch {
            try {
                // Llama al servicio de API para eliminar el pago por su ID
                val response = apiService.deletePayment(paymentId)

                if (response.isSuccessful) {
                    // El pago se eliminó exitosamente, puedes manejar la respuesta aquí
                } else {
                    // Ocurrió un error en la solicitud, puedes manejarlo según tus necesidades
                }
            } catch (e: Exception) {
                // Manejar errores en la solicitud, por ejemplo, problemas de red
            }
        }
    }
}
