package com.gpulenta.quipu.Notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.gpulenta.quipu.R

@Composable
fun ImageWithText() {
    val imageUrl1 =
        "https://realplaza.vtexassets.com/arquivos/ids/29913647-1200-auto?v=637957478142600000&width=1200&height=auto&aspect=true"

    val imageUrl3 =
        "https://d598hd2wips7r.cloudfront.net/catalog/product/cache/b3b166914d87ce343d4dc5ec5117b502/6/Q/6QW26LA-1_T1679071267.png"

    val imageUrl2 =
        "https://smartphonesperu.pe/wp-content/uploads/2020/03/SMARTHPONESPERU_0011_S20-CLOUD-1.jpg" // Reemplaza con la URL de tu segunda imagen

    Image(painter = painterResource(id = R.drawable.ic_shape), contentDescription = "shape",
        modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds
    )

    var isFavorite1 by remember { mutableStateOf(false) }
    var isFavorite2 by remember { mutableStateOf(false) }
    var isFavorite3 by remember { mutableStateOf(false) }
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp) // Espacio entre los elementos
        ) {
            Text(
                text = "Notificaciones",
                style = MaterialTheme.typography.headlineSmall
            )

            // Primer conjunto de imagen y textos
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Mostrar la primera imagen al lado del primer texto
                Image(
                    painter = rememberImagePainter(data = imageUrl1),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )

                // Columna para los textos
                Column {
                    // Primer texto
                    Text(
                        text = "Nuevo Producto!",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )

                    // Segundo texto
                    Text(
                        text = "Laptp ReDragon Ga...",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                // Botón de corazón 1
                IconButton(
                    onClick = { isFavorite1 = !isFavorite1 },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    content = {
                        Icon(
                            imageVector = if (isFavorite1) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = if (isFavorite1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                        )
                    }
                )
            }

            // Segundo conjunto de imagen y textos
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Mostrar la segunda imagen al lado del primer texto
                Image(
                    painter = rememberImagePainter(data = imageUrl2),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )

                // Columna para los textos
                Column {
                    // Primer texto
                    Text(
                        text = "Algo para ti!",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold

                    )

                    // Segundo texto
                    Text(
                        text = "Telefono Samsung ult...",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                // Botón de corazón 2
                IconButton(
                    onClick = { isFavorite2 = !isFavorite2 },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    content = {
                        Icon(
                            imageVector = if (isFavorite2) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = if (isFavorite2) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                        )
                    }
                )
            }

            // Tercer conjunto de imagen y textos
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Mostrar la tercera imagen al lado del primer texto
                Image(
                    painter = rememberImagePainter(data = imageUrl3),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )

                // Columna para los textos
                Column {
                    // Primer texto
                    Text(
                        text = "Ultima Oportunidad!",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )

                    // Segundo texto
                    Text(
                        text = "Laptop HP Intel...",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                // Botón de corazón 3
                IconButton(
                    onClick = { isFavorite3 = !isFavorite3 },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    content = {
                        Icon(
                            imageVector = if (isFavorite3) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = if (isFavorite3) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                        )
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun ImageWithTextPreview() {
    Surface {
        ImageWithText()
    }
}