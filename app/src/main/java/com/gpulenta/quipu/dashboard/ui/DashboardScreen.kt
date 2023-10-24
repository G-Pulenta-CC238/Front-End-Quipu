package com.gpulenta.quipu.dashboard.ui

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.gpulenta.quipu.R
import com.gpulenta.quipu.dashboard.data.ProductData
import com.gpulenta.quipu.shared.RetrofitClient


@Preview
@Composable
fun DashboardScreen() {
    Image(painter = painterResource(id = R.drawable.ic_shape), contentDescription = "shape",
        modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds
    )
    val products = remember { mutableStateOf<List<ProductData>>(emptyList()) }
    LaunchedEffect(Unit) {
        val apiService = RetrofitClient.apiService
        try {
            val response = apiService.getProducts()
            products.value = response
        } catch (e: Exception) {
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Show the RecyclerView with the list of products
        ProductRecyclerView(products.value)

    }
}


@Composable
fun ProductRecyclerView(products: List<ProductData>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(products.size) { index ->
            // Display each product in the list
            ProductListItem(products[index])
        }
    }
}

@Composable
fun ProductListItem(product: ProductData) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White.copy(alpha = 0.9f), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            // Product Name
            Text(
                text = product.productName,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                textAlign = TextAlign.Center
            )

            // Description
            Text(
                text = product.productDescription,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 24.dp),
                textAlign = TextAlign.Start
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
                textAlign = TextAlign.Start
            )

            // Rating
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
                textAlign = TextAlign.Start
            )
            //Category


            // Display the image from the URL
            product.productImageUrl?.let { imageUrl ->
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
                        .aspectRatio(1f)
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

        }
    }
}







