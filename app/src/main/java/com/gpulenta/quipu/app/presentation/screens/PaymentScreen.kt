package com.gpulenta.quipu.app.presentation.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gpulenta.quipu.R
import com.gpulenta.quipu.app.domain.model.response.Payment
import com.gpulenta.quipu.app.presentation.viewmodels.PaymentViewModel

@Composable
fun PaymentScreen(paymentViewModel: PaymentViewModel, navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val idUser = sharedPreferences.getLong("id", 0L)

    var paymentId by remember { mutableStateOf("") }
    var paymentNumber by remember { mutableStateOf("") }
    var paymentExpiration by remember { mutableStateOf("") }
    var paymentCvv by remember { mutableStateOf("") }
    var payment: Payment? by remember { mutableStateOf(null) }

    // Verificar si existe un registro de pago para el usuario
    LaunchedEffect(idUser) {
        try {
            val paymentResponse = paymentViewModel.fetchPaymentData(idUser)
            if (paymentResponse.isSuccessful) {
                payment = paymentResponse.body()
                // Hacer algo con el objeto Payment si es necesario
                paymentId = payment?.id.toString()
                paymentNumber = payment?.paymentNumber.orEmpty()
                paymentExpiration = payment?.paymentExpiration.orEmpty()
                paymentCvv = payment?.paymentCvv.orEmpty()
            } else {
                // Manejar errores de la respuesta si es necesario
            }
        } catch (e: Exception) {
            // Manejar excepciones o errores de red
        }
    }
    Image(
        painter = painterResource(id = R.drawable.background_quipu), contentDescription = "shape",
        modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .padding(24.dp)
            .padding(top = 50.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(Color(0f, 0f, 0f, 0.1f)),

    ) {
        OutlinedTextField(
            value = paymentNumber,
            onValueChange = { paymentNumber = it },
            label = { Text("Payment Number") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        OutlinedTextField(
            value = paymentExpiration,
            onValueChange = { paymentExpiration = it },
            label = { Text("Expiration Date") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        OutlinedTextField(
            value = paymentCvv,
            onValueChange = { paymentCvv = it },
            label = { Text("CVV") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        val context = LocalContext.current
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val idUser = sharedPreferences.getLong("id", 0L)
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Aquí puedes crear un objeto Payment con los datos ingresados
                val payment = Payment(
                    id = paymentId,
                    paymentNumber = paymentNumber,
                    paymentExpiration = paymentExpiration,
                    paymentCvv = paymentCvv,
                    userId = idUser
                )

                // Llamar a la función correcta del ViewModel para enviar el pago
                paymentViewModel.createPayment(payment)

                // Navegar a la pantalla de confirmación u otra pantalla
                navController.navigate("Payment")
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            Text(text = "Submit Payment")
        }
        Button(
            onClick = {
                if (payment != null) {
                    // Llama a la función en el ViewModel para eliminar el pago
                    paymentViewModel.deletePayment(paymentId.toLong())
                    //delete

                    navController.navigate("Dashboard")

                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            Text(text = "Delete Payment")
        }
    }
}
