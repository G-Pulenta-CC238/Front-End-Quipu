package com.gpulenta.quipu.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpulenta.quipu.data.remote.retrofit.RetrofitClient
import com.gpulenta.quipu.data.remote.service.ApiService
import com.gpulenta.quipu.domain.model.response.CartItemResponse
import kotlinx.coroutines.launch


class ShoppingCartViewModel(private val context: Context) : ViewModel() {

    private val apiService: ApiService = RetrofitClient.apiService

    private var _cartItems = MediatorLiveData<List<CartItemResponse>>()
    val cartItems: LiveData<List<CartItemResponse>> = _cartItems

    init {
        fetchCartItems()
    }
    fun fetchCartItems() {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val shoppingCartId = sharedPreferences.getLong("idShoppingCart", 0L)

        if (shoppingCartId == 0L) {
            // Handle the case where shoppingCartId is not set in shared preferences
            _cartItems.value = emptyList()
        } else {
            viewModelScope.launch {
                try {
                    val response = apiService.getCartItemsByShoppingCart(shoppingCartId)
                    _cartItems.value = response // Assuming this call returns the list of cart items
                } catch (e: Exception) {
                    // Handle the error, e.g., show a message to the user
                    _cartItems.value = emptyList()
                }
            }
        }
    }
    fun removeItemFromCart(cartItemId: Long) {
        viewModelScope.launch {
            try {
                apiService.deleteCartItemById(cartItemId)
                fetchCartItems()
            } catch (e: Exception) {
            }
        }
    }

}
