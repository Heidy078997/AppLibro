package com.heidygonzalez.applibros.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale

import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage

import androidx.compose.runtime.livedata.observeAsState

@Composable
fun SearchScreen(viewModel: SearchViewModel, navController: NavController) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        var query by remember { mutableStateOf("") }

        // Campo de búsqueda
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Buscar libro...") },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = {
                            query = ""
                            viewModel.clearSearch()
                        }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Borrar")
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { viewModel.buscarLibros(query) }) {
                Text("Buscar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isLoading) {
            CircularProgressIndicator()
        }

        uiState.errorMessage?.let {
            Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 16.dp))
        } ?: run {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(uiState.books) { book ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        // Contenedor para la imagen de portada
                        if (!book.portadaUrl.isNullOrEmpty()) {
                            Box(
                                modifier = Modifier
                                    .size(100.dp, 150.dp)
                                    .background(Color.Gray, shape = RectangleShape)
                                    .padding(4.dp)
                            ) {
                                AsyncImage(
                                    model = book.portadaUrl,
                                    contentDescription = "Portada del libro",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }

                        // Espacio para los datos del libro
                        Column(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .fillMaxWidth()
                        ) {
                            Text(text = "Título: ${book.titulo}", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "ISBN: ${book.isbn}", style = MaterialTheme.typography.bodyMedium)

                            // Enlace para ver más detalles
                            Text(
                                text = "Ver",
                                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Blue),
                                modifier = Modifier.clickable {
                                    // Navegar a la pantalla de detalles del libro
                                    navController.navigate("bookDetailScreen/${book.libroId}")
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        // Botón para agregar un libro
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("addBookScreen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar un libro")
        }
    }
}
