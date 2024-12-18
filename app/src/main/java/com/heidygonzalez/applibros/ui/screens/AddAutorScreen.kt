package com.heidygonzalez.applibros.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.heidygonzalez.applibros.data.BookRepository
import com.heidygonzalez.applibros.model.Autor
import com.heidygonzalez.applibros.network.BookApiService
import kotlinx.coroutines.delay

@Composable
fun AddAutorScreen(viewModel: AddAutorViewModel, navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var showSuccessMessage by remember { mutableStateOf(false) } // Control para el mensaje de éxito

    // Contenedor principal
    Column(modifier = Modifier.padding(16.dp)) {
        // Botón de volver atrás
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Volver atrás")
        }

        // Campo Nombre
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del Autor") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Guardar
        Button(
            onClick = {
                if (nombre.isNotEmpty()) {
                    // Llamada al método agregarAutor del ViewModel con onResult
                    viewModel.agregarAutor(Autor(autorId = 0, nombre = nombre)) { success ->
                        if (success) {
                            showSuccessMessage = true // Mostrar mensaje de éxito
                        } else {
                            errorMessage = "Error al agregar el autor."
                        }
                    }
                } else {
                    errorMessage = "El nombre del autor no puede estar vacío."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Autor")
        }

        // Mostrar mensaje de error
        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = errorMessage, color = Color.Red)
        }

        // Mostrar mensaje de éxito
        if (showSuccessMessage) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Autor agregado", color = Color.Green)

            // Utilizar LaunchedEffect para navegar después de 2 segundos
            LaunchedEffect(showSuccessMessage) {
                delay(2000) // Espera 2 segundos
                navController.navigate("addBookScreen") {
                    popUpTo("addBookScreen") { inclusive = false }
                }
            }
        }
    }
}
