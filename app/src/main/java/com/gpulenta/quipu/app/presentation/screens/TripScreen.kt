package com.gpulenta.quipu.presentation.screens

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FlightLand
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gpulenta.quipu.domain.model.response.Trip
import com.gpulenta.quipu.presentation.viewmodels.TripViewModel
import java.util.Calendar

@Composable
fun TripScreen(
    viewModel: TripViewModel,
    navController: NavHostController
) {


    BackGround(modifier = Modifier.fillMaxSize())
    var origin by remember { mutableStateOf(TextFieldValue()) }
    var destination by remember { mutableStateOf(TextFieldValue()) }
    var date by remember { mutableStateOf("") }

    val anio: Int
    val mes: Int
    val dia: Int
    val mCalendar = Calendar.getInstance()
    anio = mCalendar.get(Calendar.YEAR)
    mes = mCalendar.get(Calendar.MONTH)
    dia = mCalendar.get(Calendar.DAY_OF_MONTH)



    val mDatePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            date = "$dayOfMonth/${month + 1}/$year"
        },
        anio,
        mes,
        dia
    )

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val userId = sharedPreferences.getLong("id", 1)
    LaunchedEffect(userId) {
        viewModel.loadTripsByUser(userId)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = androidx.compose.ui.Alignment.Center)
            .padding(8.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(Color.White.copy(alpha = 0.5f))
            .padding(8.dp)
    ) {
        Text(
            text = "Create a Trip",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = origin,
            onValueChange = { origin = it },
            label = { Text("Origin") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.FlightTakeoff,
                    contentDescription = "DestinationIcon"
                )
            }
        )

        OutlinedTextField(
            value = destination,
            onValueChange = { destination = it },
            label = { Text("Destination") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.FlightLand,
                    contentDescription = "DestinationIcon"
                )
            }
        )

        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Date") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { mDatePickerDialog.show() },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "DateIcon",
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { mDatePickerDialog.show() }
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.createTrip(
                    origin.text,
                    destination.text,
                    date.toString(),
                    userId,
                    onSuccess = {
                        navController.navigate("tripCreated")
                    },

                    onError = { errorMessage ->
                        // Manejar el error, por ejemplo, mostrar un mensaje de error
                        // Puedes usar un Snackbar o Toast para mostrar el mensaje de error
                    }
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5E17EB),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .padding(8.dp)
        ) {
            Text("CREATE TRIP")
        }

        Spacer(modifier = Modifier.height(16.dp))

        val trips by viewModel.trips.observeAsState(emptyList())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Your Trips",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(8.dp)
            )

            if (trips.isNotEmpty()) {
                LazyColumn {
                    itemsIndexed(trips) { _, trip ->
                        TripItem(trip)
                    }
                }
            }  else {
                // Mostrar un mensaje si no hay viajes disponibles
                Text(
                    text = "No trips available.",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
@Composable
fun TripItem(trip: Trip) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Origin: ${trip.origin}")
            Text(text = "Destination: ${trip.destination}")
            Text(text = "Date: ${trip.date}")
        }
    }
}