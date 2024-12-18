package com.heidygonzalez.applibros.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.heidygonzalez.applibros.model.Autor
import com.heidygonzalez.applibros.model.Book
import com.heidygonzalez.applibros.model.Genero
import androidx.compose.ui.unit.sp


@Composable
fun EditBookScreen(viewModel: EditBookViewModel, sharedViewModel: SharedBookViewModel, navController: NavController, bookId: Int) {
    val authors by viewModel.autores.collectAsState()
    val genres by viewModel.generos.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    // Estados del formulario
    var title by remember { mutableStateOf("") }
    var isbn by remember { mutableStateOf("") }
    var authorId by remember { mutableStateOf(-1) }
    var genreId by remember { mutableStateOf(-1) }
    var year by remember { mutableStateOf("") }
    var pages by remember { mutableStateOf("") }
    var synopsis by remember { mutableStateOf("") }
    var puntuacion by remember { mutableStateOf("") }
    var portadaUrl by remember { mutableStateOf("") }

    var expandedAuthor by remember { mutableStateOf(false) }
    var expandedGenre by remember { mutableStateOf(false) }

    // Estados para validaciones
    var titleError by remember { mutableStateOf(false) }
    var isbnError by remember { mutableStateOf(false) }
    var pagesError by remember { mutableStateOf(false) }
    var puntuacionError by remember { mutableStateOf(false) }
    var synopsisError by remember { mutableStateOf(false) }
    var portadaUrlError by remember { mutableStateOf(false) }

    var isInitialized by remember { mutableStateOf(false) }

    // Cargar datos del libro al iniciar
    LaunchedEffect(bookId) {
        viewModel.obtenerLibro(bookId)
    }

    // Asignar los valores iniciales si no se ha hecho antes
    if (!isInitialized && uiState.book != null) {
        uiState.book?.let { book ->
            title = book.titulo
            isbn = book.isbn
            authorId = book.autorId
            genreId = book.generoId
            year = book.anhoPublicacion.toString()
            pages = book.paginas.toString()
            synopsis = book.sinopsis
            puntuacion = book.puntuacion.toString()
            portadaUrl = book.portadaUrl.toString()
        }
        isInitialized = true
    }

    //recargar probar si funca

    LaunchedEffect(Unit) {
        viewModel.cargarAutores()
    }


    Column(modifier = Modifier.padding(16.dp)) {
        // Botón de retroceso (Back)
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }

        // Aquí va el resto de tu formulario y otros campos...

    Column(modifier = Modifier.padding(16.dp)) {
        // Campo Título
        TextField(
            value = title,
            onValueChange = { title = it; titleError = it.isBlank() },
            label = { Text("Título") },
            isError = titleError,
            modifier = Modifier.fillMaxWidth()
        )
        if (titleError) Text("El título es obligatorio", color = Color.Red, fontSize = 12.sp)

        // Campo ISBN
        TextField(
            value = isbn,
            onValueChange = { isbn = it; isbnError = !(it.length in 10..13) },
            label = { Text("ISBN") },
            isError = isbnError,
            modifier = Modifier.fillMaxWidth()
        )
        if (isbnError) Text(
            "El ISBN debe tener entre 10 y 13 dígitos",
            color = Color.Red,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Dropdown Autor
        TextField(
            value = authors.find { it.autorId == authorId }?.nombre ?: "Selecciona un autor",
            onValueChange = {},
            label = { Text("Autor") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expandedAuthor = !expandedAuthor }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Expandir Autor"
                    )
                }
            }
        )
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

        //nuevo  codigo aqui

        // Botón para añadir autor
        Text(
            text = "Añadir Autor",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable {
                    navController.navigate("AddAutorScreen") {

                    }
                }
                .padding(8.dp),
            fontSize = 14.sp
        )



        //hasta aca

        Spacer(modifier = Modifier.height(8.dp))

        // Dropdown Género
        TextField(
            value = genres.find { it.generoId == genreId }?.descripcion ?: "Selecciona un género",
            onValueChange = {},
            label = { Text("Género") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expandedGenre = !expandedGenre }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Expandir Género"
                    )
                }
            }
        )
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

        Spacer(modifier = Modifier.height(8.dp))

        // Campo Páginas
        TextField(
            value = pages,
            onValueChange = {
                pages = it
                pagesError = it.toIntOrNull()?.let { value -> value <= 0 } ?: true
            },
            label = { Text("Páginas") },
            isError = pagesError,
            modifier = Modifier.fillMaxWidth()
        )
        if (pagesError) Text(
            "Las páginas deben ser un número mayor a 0",
            color = Color.Red,
            fontSize = 12.sp
        )

        // Campo Puntuación
        TextField(
            value = puntuacion,
            onValueChange = {
                puntuacion = it
                puntuacionError = it.toDoubleOrNull()?.let { value -> value !in 1.0..10.0 } ?: true
            },
            label = { Text("Puntuación") },
            isError = puntuacionError,
            modifier = Modifier.fillMaxWidth()
        )
        if (puntuacionError) Text(
            "La puntuación debe ser entre 1.0 y 10.0",
            color = Color.Red,
            fontSize = 12.sp
        )

        // Campo Sinopsis
        TextField(
            value = synopsis,
            onValueChange = { synopsis = it; synopsisError = it.isBlank() },
            label = { Text("Sinopsis") },
            isError = synopsisError,
            modifier = Modifier.fillMaxWidth()
        )
        if (synopsisError) Text("La sinopsis es obligatoria", color = Color.Red, fontSize = 12.sp)

        // Campo Portada URL
        TextField(
            value = portadaUrl,
            onValueChange = {
                portadaUrl = it; portadaUrlError =
                it.isBlank() || !android.util.Patterns.WEB_URL.matcher(it).matches()
            },
            label = { Text("URL de la Portada") },
            isError = portadaUrlError,
            modifier = Modifier.fillMaxWidth()
        )
        if (portadaUrlError) Text(
            "La portada debe ser una URL válida",
            color = Color.Red,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Guardar

        // Botón Guardar
        Button(
            onClick = {
                titleError = title.isBlank()
                isbnError = !(isbn.length in 10..13)
                pagesError = pages.toIntOrNull()?.let { it <= 0 } ?: true
                puntuacionError = puntuacion.toDoubleOrNull()?.let { it !in 1.0..10.0 } ?: true
                synopsisError = synopsis.isBlank()
                portadaUrlError =
                    portadaUrl.isBlank() || !android.util.Patterns.WEB_URL.matcher(portadaUrl)
                        .matches()

                if (!titleError && !isbnError && !pagesError && !puntuacionError && !synopsisError && !portadaUrlError) {
                    val updatedBook = Book(
                        libroId = bookId,
                        titulo = title,
                        isbn = isbn,
                        anhoPublicacion = year.toIntOrNull() ?: 0,
                        generoId = genreId,
                        autorId = authorId,
                        paginas = pages.toIntOrNull() ?: 0,
                        portadaUrl = portadaUrl,
                        puntuacion = puntuacion.toDoubleOrNull() ?: 0.0,
                        fechaRegistro = "",
                        sinopsis = synopsis,
                        autor = authors.find { it.autorId == authorId } ?: Autor(0, ""),
                        genero = genres.find { it.generoId == genreId } ?: Genero(0, "")
                    )
                    viewModel.actualizarLibro(updatedBook)

                    //se agrega

                    sharedViewModel.notifyBookUpdated(updatedBook)

                     // Notificar a otras pantallas que se ha actualizado

                    //


                    // Limpiar campos después de actualizar
                    title = ""
                    isbn = ""
                    authorId = -1
                    genreId = -1
                    year = ""
                    pages = ""
                    synopsis = ""
                    puntuacion = ""
                    portadaUrl = ""
                } else {
                    Log.d("Validation", "Error en las validaciones")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Actualizar")
        }


    }
        // Mensajes de éxito o error
        if (uiState.errorMessage != null) {
            Text(text = uiState.errorMessage!!, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
        }
        if (uiState.isSuccess) {
            Text(text = "Libro actualizado con éxito", color = Color.Green, modifier = Modifier.padding(top = 8.dp))
        }

    }
}



