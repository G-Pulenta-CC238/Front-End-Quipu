package com.gpulenta.quipu.presentation.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpulenta.quipu.data.remote.retrofit.RetrofitClient
import com.gpulenta.quipu.domain.model.request.CartItemData
import com.gpulenta.quipu.domain.model.request.ShoppingCarta
import com.gpulenta.quipu.domain.model.response.ProductResponse
import kotlinx.coroutines.launch

class NewViewModel : ViewModel() {
    fun onBuyProductClicked(context: Context, product: ProductResponse) {
        viewModelScope.launch {
            try {
                val sharedPreferences =
                    context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val shoppingCartId = sharedPreferences.getLong("idShoppingCart", 0L)
                if (shoppingCartId != 0L) {
                    val cartItemData = CartItemData(
                        productQuantity = 1,
                        cartSubtotal = product.productPrice,
                        productId = product.id,
                        shoppingCart = ShoppingCarta(id = shoppingCartId)
                    )
                    Log.d("DashboardViewModel", "CartItemData : $cartItemData")
                    val apiService = RetrofitClient.apiService
                    val response = apiService.createCartItem(cartItemData)
                    Log.d("DashboardViewModel", "CartItemData : $cartItemData")
                } else {
                    Log.e("DashboardViewModel", "El ID del carrito de compras es inválido")
                }
            } catch (e: Exception) {
                Log.e("DashboardViewModel", "Error al agregar el producto al carrito: ${e.message}")
            }
        }
    }
}