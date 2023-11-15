package com.gpulenta.quipu.presentation.screens


import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.gpulenta.quipu.R
import com.gpulenta.quipu.data.remote.retrofit.RetrofitClient
import com.gpulenta.quipu.domain.model.response.ProductResponse
import com.gpulenta.quipu.presentation.viewmodels.DashboardViewModel

@Composable
fun DashboardScreen(viewModel: DashboardViewModel, navController: NavHostController) {
    var selectedNavItem by remember { mutableStateOf(0) }
    val context = LocalContext.current

    Image(
        painter = painterResource(id = R.drawable.background_quipu), contentDescription = "shape",
        modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds
    )
    val products = remember { mutableStateOf<List<ProductResponse>>(emptyList()) }
    LaunchedEffect(Unit) {
        val apiService = RetrofitClient.apiService
        try {
            val response = apiService.getProducts()
            products.value = response
        } catch (e: Exception) {
        }
    }
    var searchTerm by remember { mutableStateOf("") }

    val filteredProducts = if (searchTerm.isBlank()) {
        products.value
    } else {
        products.value.filter { product ->
            product.productName.contains(searchTerm, ignoreCase = true)
        }
    }
    Column(
        modifier = Modifier
            .padding(top = 56.dp),
    ) {
        OutlinedTextField(
            value = searchTerm,
            onValueChange = { searchTerm = it },
            label = { Text(text = "Search") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.8f))
                .padding(16.dp)
        )
        ProductRecyclerView(filteredProducts, viewModel)
    }
    BottomNavigation(
        modifier = Modifier
            .height(64.dp),
        backgroundColor = Color(0xFF5E17EB),
    ) {

        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title, tint = Color.White) },
                label = {
                    Text(
                        text = item.title,
                        color = Color.White,
                        style = TextStyle(fontSize = 10.sp)
                    )
                },
                selected = index == selectedNavItem,
                onClick = {
                    selectedNavItem = index
                    when (item.title) {
                        "Offer" -> navController.navigate("Offer")
                        "News" -> navController.navigate("New")
                        "My Offer" -> navController.navigate("MyOffer")
                        "Exit" -> {
                            val sharedPreferences =
                                context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                            with(sharedPreferences.edit()) {
                                clear()
                                apply()
                            }
                            navController.navigate("SignIn")
                        }
                        "Trips" -> navController.navigate("Trip")
                        "Profile" -> navController.navigate("Profile")
                        "Cart" -> navController.navigate("ShoppingCart")
                        else -> {}
                    }
                },
            )
        }

    }
}

data class NavItem(val title: String, val icon: ImageVector)

val items = listOf(
    NavItem("Offer", Icons.Default.DeveloperBoard),
    NavItem("My Offer", Icons.Default.DeveloperBoard),
    NavItem("News", Icons.Default.Grade),
    NavItem("Cart", Icons.Default.ShoppingCart),
    NavItem("Trips", Icons.Default.Flight),
    NavItem("Profile", Icons.Default.Person),
)

@Composable
fun ProductRecyclerView(products: List<ProductResponse>, viewModel: DashboardViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(products.size) { index ->
            ProductListItem(products[index], viewModel)
        }
    }
}

@Composable
fun ProductListItem(product: ProductResponse, viewModel: DashboardViewModel) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(25.dp))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(
                text = product.productName,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp)
            )
            Text(
                text = product.productDescription,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                textAlign = TextAlign.Justify,
                style = TextStyle(fontSize = 14.sp)
            )
            Text(
                text = buildAnnotatedString {
                    append("Price: $")
                    append(product.productPrice.toString())
                },
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 12.dp),
                textAlign = TextAlign.Start,
                style = TextStyle(fontSize = 16.sp)
            )
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
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    contentScale = ContentScale.FillWidth
                )
            }
            Text(
                text = buildAnnotatedString {
                    append("Rating: ")
                    repeat(product.productRating) {
                        append("★")
                    }
                },
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 6.dp),
                textAlign = TextAlign.End,
                style = TextStyle(fontSize = 14.sp)
            )
            Button(
                onClick = {
                    viewModel.onBuyProductClicked(context, product)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5E17EB),
                    contentColor = Color.White
                ),
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart, // Cambia a tu icono de carrito deseado
                    contentDescription = "CartIcon",
                    tint = Color.White
                )
                Text(text = "Buy")
            }
        }
    }
}