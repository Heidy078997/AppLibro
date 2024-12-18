package com.heidygonzalez.applibros.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.heidygonzalez.applibros.model.Autor
import com.heidygonzalez.applibros.model.Book
import com.heidygonzalez.applibros.model.Genero
import kotlinx.coroutines.delay



@Composable
fun AddBookScreen(viewModel: ABookViewModel, navController: NavController) {
    val authors by viewModel.autores.collectAsState() // Lista de autores
    val genres by viewModel.generos.collectAsState() // Lista de géneros
    val uiState by viewModel.uiState.collectAsState()

    // Inicializa las variables con valores por defecto
    var title by remember { mutableStateOf("") }
    var isbn by remember { mutableStateOf("") }
    var authorId by remember { mutableStateOf(authors.firstOrNull()?.autorId ?: -1) }
    var genreId by remember { mutableStateOf(genres.firstOrNull()?.generoId ?: -1) }
    var year by remember { mutableStateOf("") }
    var pages by remember { mutableStateOf("") }
    var synopsis by remember { mutableStateOf("") }
    var puntuacion by remember { mutableStateOf("") }  // Nuevo campo para puntuación
    var portadaUrl by remember { mutableStateOf("") }  // Nuevo campo para portada URL
    var errorMessage by remember { mutableStateOf("") }

    // Variables para los mensajes de éxito o error
    var successMessage by remember { mutableStateOf("") }
    var errorMessageVisible by remember { mutableStateOf(false) }

    // Control de visibilidad de los dropdowns
    var expandedAuthor by remember { mutableStateOf(false) }
    var expandedGenre by remember { mutableStateOf(false) }

    // Verifica que las listas de autores y géneros no estén vacías
    if (authors.isEmpty() || genres.isEmpty()) {
        return
    }

    // Desaparecer el mensaje de éxito o error después de unos segundos
    LaunchedEffect(successMessage, errorMessage) {
        if (successMessage.isNotEmpty()) {
            delay(3000)
            successMessage = "" // Limpiar mensaje de éxito
        }
        if (errorMessageVisible) {
            delay(3000)
            errorMessage = "" // Limpiar mensaje de error
            errorMessageVisible = false
        }
    }

    //recargar

    LaunchedEffect(Unit) {
        viewModel.cargarAutores()
    }

    //hasta aqui

    Column(modifier = Modifier.padding(16.dp)) {
        // Botón de "Atrás"
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Atrás")
        }

        // Campo Título
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo ISBN
        TextField(
            value = isbn,
            onValueChange = { isbn = it },
            label = { Text("ISBN") },
            modifier = Modifier.fillMaxWidth()
        )

        // Dropdown para autores
        TextField(
            value = authors.find { it.autorId == authorId }?.nombre ?: "Selecciona un autor",
            onValueChange = {},
            label = { Text("Autor") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expandedAuthor = !expandedAuthor }) {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Expandir menú")
                }
            }
        )

        //se añade prueba acá

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = {
                navController.navigate("AddAutorScreen") // ir a la pantalla de agregar autor
            }) {
                Text("Añadir Autor")
            }
        }


        //hasta acá

        // DropdownMenu para autores
        DropdownMenu(
            expanded = expandedAuthor,
            onDismissRequest = { expandedAuthor = false }
        ) {
            authors.forEach { author ->
                DropdownMenuItem(
                    onClick = {
                        authorId = author.autorId
                        expandedAuthor = false
                    },
                    text = { Text(text = author.nombre) }
                )
            }
        }

        // Dropdown para géneros
        TextField(
            value = genres.find { it.generoId == genreId }?.descripcion ?: "Selecciona un género",
            onValueChange = {},
            label = { Text("Género") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expandedGenre = !expandedGenre }) {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Expandir menú")
                }
            }
        )

        // DropdownMenu para géneros
        DropdownMenu(
            expanded = expandedGenre,
            onDismissRequest = { expandedGenre = false }
        ) {
            genres.forEach { genre ->
                DropdownMenuItem(
                    onClick = {
                        genreId = genre.generoId
                        expandedGenre = false
                    },
                    text = { Text(text = genre.descripcion) }
                )
            }
        }

        // Campo Año de Publicación con validación
        TextField(
            value = year,
            onValueChange = { year = it },
            label = { Text("Año de publicación") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Campo de Páginas con validación
        TextField(
            value = pages,
            onValueChange = { pages = it },
            label = { Text("Número de páginas") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Sinopsis
        TextField(
            value = synopsis,
            onValueChange = { synopsis = it },
            label = { Text("Sinopsis") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo de Puntuación
        TextField(
            value = puntuacion,
            onValueChange = { puntuacion = it },
            label = { Text("Puntuación (1-10)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Campo de URL de la Portada
        TextField(
            value = portadaUrl,
            onValueChange = { portadaUrl = it },
            label = { Text("URL de la portada") },
            modifier = Modifier.fillMaxWidth()
        )

        // Muestra la imagen de la portada (si se proporciona la URL)
        if (portadaUrl.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Gray, shape = RectangleShape)
            ) {
                // Intenta cargar la imagen desde la URL
                AsyncImage(
                    model = portadaUrl,
                    contentDescription = "Portada del libro",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    onError = {
                        // Si ocurre un error al cargar la imagen
                        Log.e("AddBookScreen", "Error al cargar la imagen de portada")
                    }
                )
            }
        }

        // Botón Guardar
        Button(
            onClick = {
                // Validación del título (no puede estar vacío)
                if (title.isEmpty()) {
                    errorMessage = "El título es obligatorio"
                    errorMessageVisible = true
                }
                // Validación del ISBN (debe ser numérico y entre 10 y 13 dígitos)
                else if (isbn.isEmpty() || !isbn.all { it.isDigit() } || isbn.length !in 10..13) {
                    errorMessage = "El ISBN debe tener entre 10 y 13 dígitos numéricos"
                    errorMessageVisible = true
                }
                // Validación del año de publicación (mayor a 1450)
                else if (year.isEmpty() || year.toIntOrNull() == null || year.toInt() < 1450) {
                    errorMessage = "Asegúrese de que el año sea válido"
                    errorMessageVisible = true
                }
                // Validación del número de páginas (mayor a cero)
                else if (pages.isEmpty() || pages.toIntOrNull() == null || pages.toInt() <= 0) {
                    errorMessage = if (pages.toIntOrNull() == null) {
                        "Asegúrese de ingresar la cantidad de páginas en formato numérico"
                    } else {
                        "La cantidad de páginas no puede ser cero"
                    }
                    errorMessageVisible = true
                }
                // Validación de puntuación (debe estar entre 1 y 10)
                else if (puntuacion.toDoubleOrNull() == null || puntuacion.toDouble() < 1 || puntuacion.toDouble() > 10) {
                    errorMessage = "La puntuación debe estar entre 1 y 10"
                    errorMessageVisible = true
                } else {
                    // Si todo es válido, se guarda el libro
                    val nuevoLibro = Book(
                        libroId = 0,
                        titulo = title,
                        isbn = isbn,
                        anhoPublicacion = year.toInt(),
                        generoId = genreId,
                        autorId = authorId,
                        paginas = pages.toInt(),
                        portadaUrl = portadaUrl,
                        puntuacion = puntuacion.toDouble(),
                        fechaRegistro = "",
                        sinopsis = synopsis,
                        autor = authors.find { it.autorId == authorId } ?: Autor(0, "", ),
                        genero = genres.find { it.generoId == genreId } ?: Genero(0, "")
                    )
                    viewModel.agregarLibro(nuevoLibro)

                    successMessage = "Libro guardado con éxito"
                    errorMessage = ""
                    title = ""
                    isbn = ""
                    authorId = authors.firstOrNull()?.autorId ?: -1
                    genreId = genres.firstOrNull()?.generoId ?: -1
                    year = ""
                    pages = ""
                    synopsis = ""
                    portadaUrl = ""
                    puntuacion = ""
                }


            },
            modifier = Modifier.fillMaxWidth(),
            enabled = title.isNotEmpty() && isbn.isNotEmpty() && year.isNotEmpty() && pages.isNotEmpty() && puntuacion.isNotEmpty() && portadaUrl.isNotEmpty()
        ) {
            Text("Guardar")
        }

        // Mensajes de éxito o error
        if (errorMessageVisible) {
            Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
        }
        if (successMessage.isNotEmpty()) {
            Text(text = successMessage, color = Color.Green, modifier = Modifier.padding(top = 8.dp))
        }
    }
}
