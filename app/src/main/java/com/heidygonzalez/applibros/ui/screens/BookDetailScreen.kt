package com.heidygonzalez.applibros.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun BookDetailScreen(bookId: String, viewModel: SearchViewModel, navController: NavController) {

    val uiState by viewModel.uiState.collectAsState()

    // Buscar el libro en la lista de libros usando el libroId
    val book = uiState.books.find { it.libroId == bookId.toIntOrNull() }

    if (book != null) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ) {

            // Contenedor con scroll para los detalles del libro
            Column(modifier = Modifier
                .weight(1f) // El contenido principal ocupa el espacio disponible
                .verticalScroll(rememberScrollState()) // Permite desplazamiento
            ) {
                // Imagen de portada
                if (!book.portadaUrl.isNullOrEmpty()) {
                    Box(
                        modifier = Modifier
                            .size(200.dp, 300.dp)
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

                Spacer(modifier = Modifier.height(16.dp))

                // Título del libro
                Text(
                    text = "Título:",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(text = book.titulo, style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(8.dp))

                // ISBN del libro
                Text(
                    text = "ISBN:",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(text = book.isbn, style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(8.dp))

                // Autor
                Text(
                    text = "Autor:",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                val autor = book.autor
                if (autor != null) {
                    Text(text = autor.nombre, style = MaterialTheme.typography.bodyMedium)
                } else {
                    Text(text = "Desconocido", style = MaterialTheme.typography.bodyMedium)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Género
                Text(
                    text = "Género:",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                val genero = book.genero
                if (genero != null) {
                    Text(text = genero.descripcion, style = MaterialTheme.typography.bodyMedium)
                } else {
                    Text(text = "No disponible", style = MaterialTheme.typography.bodyMedium)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Páginas
                Text(
                    text = "Páginas:",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                val paginas = book.paginas
                if (paginas != null) {
                    Text(text = paginas.toString(), style = MaterialTheme.typography.bodyMedium)
                } else {
                    Text(text = "No disponible", style = MaterialTheme.typography.bodyMedium)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Puntuación
                Text(
                    text = "Puntuación:",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                val puntuacion = book.puntuacion
                if (puntuacion != null) {
                    Text(text = puntuacion.toString(), style = MaterialTheme.typography.bodyMedium)
                } else {
                    Text(text = "No disponible", style = MaterialTheme.typography.bodyMedium)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Sinopsis
                Text(
                    text = "Sinopsis:",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(text = book.sinopsis, style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(32.dp)) // Espacio adicional antes de los botones
            }

            // Pie de página con los botones fijos
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White) // Fondo blanco para el pie de página
            ) {
                // Botones Modificar y Eliminar centrados
                Row(
                    horizontalArrangement = Arrangement.Center, // Alineación centrada
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Botón Modificar
                    Button(
                        onClick = { /* Aquí irá la lógica para modificar */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF57C00)), // Naranja
                        modifier = Modifier
                            .height(40.dp)
                            .width(120.dp)
                            .padding(4.dp)
                    ) {
                        Text("Modificar", style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp))
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Botón Eliminar
                    Button(
                        onClick = { /* Aquí irá la lógica para eliminar */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)), // Rojo
                        modifier = Modifier
                            .height(40.dp)
                            .width(120.dp)
                            .padding(4.dp)
                    ) {
                        Text("Eliminar", style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón Volver
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Volver")
                }
            }
        }
    } else {
        Text("No se encontró el libro", style = MaterialTheme.typography.bodyMedium)
    }
}
