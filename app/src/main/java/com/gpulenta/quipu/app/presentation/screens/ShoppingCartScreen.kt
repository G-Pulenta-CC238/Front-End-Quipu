package com.gpulenta.quipu.presentation.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.gpulenta.quipu.app.domain.model.request.Offer
import com.gpulenta.quipu.data.remote.retrofit.RetrofitClient
import com.gpulenta.quipu.domain.model.response.CartItemResponse
import com.gpulenta.quipu.domain.model.response.ProductResponse
import com.gpulenta.quipu.presentation.viewmodels.ShoppingCartViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun ShoppingCartScreen(viewModel: ShoppingCartViewModel, navController: NavHostController) {
    val context = LocalContext.current
    var cartItems by remember { mutableStateOf<List<CartItemResponse>>(emptyList()) }
    val cartItemsLiveData = viewModel.cartItems.observeAsState(emptyList())
    cartItems = cartItemsLiveData.value ?: emptyList()

    BackGround(modifier = Modifier.fillMaxSize())

    // Obtener el idShoppingCart de las preferencias compartidas
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val idShoppingCart = sharedPreferences.getLong("idShoppingCart", 0L)

    // Crear un estado para los elementos del carrito de compras
    LaunchedEffect(idShoppingCart) {
        viewModel.fetchCartItems()
    }

    // Realizar una solicitud al servidor para obtener los elementos del carrito
    LaunchedEffect(idShoppingCart) {
        if (idShoppingCart > 0) {
            val apiService = RetrofitClient.apiService
            try {
                val response = apiService.getCartItemsByShoppingCart(idShoppingCart)
                cartItems = response
            } catch (e: Exception) {
                // Manejar errores de la solicitud
            }
        }
    }
    var totalProductPrice by remember { mutableDoubleStateOf(0.0) }

    LaunchedEffect(cartItems) {
        val total = cartItems.sumOf { it.product.productPrice }
        totalProductPrice = total
    }

    // Mostrar los elementos del carrito
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Shopping Cart",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White),
            modifier = Modifier.padding(16.dp)
        )

        if (cartItems.isNotEmpty()) {
            // Mostrar la lista de elementos del carrito
            LazyColumn {
                items(cartItems) { cartItem ->
                    ShoppingCartItem(cartItem.product, cartItem, viewModel, totalProductPrice)
                }
            }
        }
        val userId = sharedPreferences.getLong("id", 1)
        val shoppingCartId = sharedPreferences.getLong("idShoppingCart", 0L)
        //
        Button(
            onClick = {
                navController.navigate("Payment")
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(36.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1621DA),
                contentColor = Color.White
            ),
        ) {
            Text("Debit Card or Credit Card")
        }

        Button(
            onClick = {
                // Realizar una solicitud POST al servidor
                val apiService = RetrofitClient.apiService
                try {
                    GlobalScope.launch {
                        val offer = Offer(
                            offerStatus = "Waiting for approval",
                            offerPrice = totalProductPrice,
                            userId = userId,
                            shoppingCartId = shoppingCartId
                        )
                        val response = apiService.createOffer(offer)
                    }
                } catch (e: Exception) {
                    // Manejar errores de la solicitud
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(36.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF17CE6A),
                contentColor = Color.White
            ),
        ) {
            Text("Oferta")
        }
    }


}

@Composable
fun ShoppingCartItem(product: ProductResponse, cartItem : CartItemResponse, viewModel: ShoppingCartViewModel, totalProductPrice: Double) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(Color.White.copy(alpha = 0.5f))
            .padding(8.dp)
    ) {

        product.productImageUrl.let { imageUrl ->
            Image(
                painter = rememberImagePainter(
                    data = imageUrl,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(125.dp)
                    .padding(8.dp)
            )
        }

        // Detalles del producto
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(
                text = product.productName,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Price: $${product.productPrice}",
                style = TextStyle(fontSize = 16.sp)
            )
            Button(
                onClick = { viewModel.removeItemFromCart(cartItem.id)}, // Llama a removeItemFromCart con el ID del carrito
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(36.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFBA1F33),
                    contentColor = Color.White
                ),
            ) {
                Text("Eliminar")
            }
        }
    }


}

