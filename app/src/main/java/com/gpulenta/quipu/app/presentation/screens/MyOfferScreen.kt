package com.gpulenta.quipu.app.presentation.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.gpulenta.quipu.R
import com.gpulenta.quipu.app.domain.model.response.OfferResponse
import com.gpulenta.quipu.app.domain.model.response.OfferStatusUpdate
import com.gpulenta.quipu.app.presentation.viewmodels.OfferViewModel
import com.gpulenta.quipu.data.remote.retrofit.RetrofitClient
import com.gpulenta.quipu.domain.model.response.CartItemResponse
import com.gpulenta.quipu.presentation.screens.itemsa

@Composable
fun MyOfferScreen(offerViewModel: OfferViewModel, navController: NavHostController) {

    val myOffers = offerViewModel.myOffers.observeAsState(initial = emptyList()).value
    var selectedNavItem by remember { mutableStateOf(0) }
    val context = LocalContext.current
    // Llamar a fetchOffers cuando sea necesario, por ejemplo, en el inicio de la pantalla
    // offerViewModel.fetchOffers()
    LaunchedEffect(key1 = true) {
        offerViewModel.fetchOffers()
        offerViewModel.getMyOffers(6L)
    }
    Image(
        painter = painterResource(id = R.drawable.background_quipu), contentDescription = "shape",
        modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        androidx.compose.material.Text(
            text = "My Offers",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(8.dp)
        )

        if (myOffers.isNotEmpty()) {
            // Muestra la lista de ofertas del usuario
            MyOffersList(myOffers = myOffers, navController = navController)
        } else {
            // Maneja cuando no hay ofertas disponibles para el usuario
            androidx.compose.material.Text(text = "No offers available for this user")
        }
    }
    Spacer(modifier = Modifier.height(64.dp))
    BottomNavigation(
        modifier = Modifier
            .height(64.dp),
        backgroundColor = Color(0xFF5E17EB),
    ) {
        itemsa.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title, tint = Color.White) },
                label = {
                    Text(
                        text = item.title,
                        color = Color.White,
                        style = TextStyle(fontSize = 12.sp)
                    )
                },
                selected = index == selectedNavItem,
                onClick = {
                    selectedNavItem = index
                    when (item.title) {
                        "Home" -> navController.navigate("Dashboard")
                        "News" -> navController.navigate("New")
                        "Offers" -> navController.navigate("Offer")
                        "Profile" -> navController.navigate("Profile")
                        "Cart" -> navController.navigate("ShoppingCart")
                        "Card" -> navController.navigate("Payment")
                        "Exit" -> {
                            val sharedPreferences =
                                context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                            with(sharedPreferences.edit()) {
                                clear()
                                apply()
                            }
                            navController.navigate("SignIn")
                        }
                        else -> {}
                    }
                },
            )
        }
    }

}

@Composable
fun MyOffersList(myOffers: List<OfferResponse>, navController: NavHostController) {
    LazyColumn {
        items(myOffers) { myOffer ->
            MyOfferItem(myOffer = myOffer, offerViewModel = viewModel() ,navController = navController)
        }
    }
}
@Composable
fun MyOfferItem(myOffer: OfferResponse, offerViewModel: OfferViewModel ,navController: NavHostController) {
    // Utiliza una estructura similar a OfferItem, ajusta según tus necesidades
    // Puedes reutilizar el componente OfferItem si ambos elementos son similares
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            androidx.compose.material.Text(
                text = "Price Offer: ${myOffer.offerPrice}",
                style = MaterialTheme.typography.body1
            )
            androidx.compose.material.Text(
                text = "ID: ${myOffer.offerStatus}",
                style = MaterialTheme.typography.body1
            )
            androidx.compose.material.Text(
                    text = "Status: ${myOffer.id}",
            style = MaterialTheme.typography.body1
            )
            val cartItems = remember { mutableStateOf<List<CartItemResponse>>(emptyList()) }

            LaunchedEffect(key1 = myOffer.shoppingCartId) {
                try {
                    val response = RetrofitClient.apiService.getCartItemsByShoppingCart(myOffer.shoppingCartId)
                    cartItems.value = response
                } catch (e: Exception) {
                    // Manejar errores según tus necesidades
                }
            }

            if (cartItems.value.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                androidx.compose.material.Text(text = "Products:", style = MaterialTheme.typography.subtitle1)
                for (cartItem in cartItems.value) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            cartItem.product.productImageUrl.let { imageUrl ->
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
                            androidx.compose.material.Text(
                                text = "Products: ${cartItem.product.productName}",
                                style = MaterialTheme.typography.body2
                            )
                            androidx.compose.material.Text(
                                text = "Quantity: ${cartItem.productQuantity}",
                                style = MaterialTheme.typography.body2
                            )
                            androidx.compose.material.Text(
                                text = "Price: ${cartItem.cartSubtotal}",
                                style = MaterialTheme.typography.body2
                            )
                        }

                    }
                }
            } else {
                androidx.compose.material.Text(
                    text = "No hay elementos en el carrito disponibles para esta oferta",
                    style = MaterialTheme.typography.body1
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.height(16.dp))

            // Botón para actualizar la oferta

// En el OfferItem Composable
            Button(
                onClick = {
                    val offerStatusUpdate = OfferStatusUpdate(
                        id = myOffer.id,
                        offerStatus = "Closed", // Cambia el estado según tus necesidades
                        offerPrice = myOffer.offerPrice, // Mantén el precio
                        userId = myOffer.userId, // Mantén el ID del usuario
                        shoppingCartId = myOffer.shoppingCartId // Mantén el ID del carrito
                    )
                    navController.navigate("Offer")
                    offerViewModel.updateOfferStatus(myOffer.id, offerStatusUpdate)
                    offerViewModel.deleteOffer(myOffer.id)
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                androidx.compose.material.Text(text = "Confirm Sale")
            }
            // Resto de la información de la oferta y botones, según tus necesidades
        }
    }
}



