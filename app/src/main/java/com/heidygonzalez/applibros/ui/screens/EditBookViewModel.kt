package com.heidygonzalez.applibros.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heidygonzalez.applibros.data.BookRepository
import com.heidygonzalez.applibros.model.Autor
import com.heidygonzalez.applibros.model.Book
import com.heidygonzalez.applibros.model.Genero
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class EditBookViewModel(private val repository: BookRepository) : ViewModel() {
    private val _authors = MutableStateFlow<List<Autor>>(emptyList())
    val autores: StateFlow<List<Autor>> = _authors

    private val _genres = MutableStateFlow<List<Genero>>(emptyList())
    val generos: StateFlow<List<Genero>> = _genres

    private val _uiState = MutableStateFlow(EditBookUiState())
    val uiState: StateFlow<EditBookUiState> = _uiState

    init {
        cargarAutores()
        cargarGeneros()
    }

    private fun cargarAutores() {
        viewModelScope.launch {
            _authors.value = repository.obtenerAutor()
        }
    }

    private fun cargarGeneros() {
        viewModelScope.launch {
            _genres.value = repository.obtenerGenero()
        }
    }

    /*fun obtenerLibro(id: Int) {
        viewModelScope.launch {
            try {
                val libro = repository.obtenerLibroPorId(id)
                _uiState.value = EditBookUiState(book = libro)
            } catch (e: Exception) {
                _uiState.value = EditBookUiState(errorMessage = "Error al obtener el libro")
            }
        }
    }*/

    fun obtenerLibro(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.obtenerLibroPorId(id)
                if (response.isSuccessful) {
                    val libro = response.body() // Extraemos el libro del cuerpo de la respuesta
                    if (libro != null) {
                        _uiState.value = EditBookUiState(book = libro)
                    } else {
                        _uiState.value = EditBookUiState(errorMessage = "Libro no encontrado")
                    }
                } else {
                    _uiState.value = EditBookUiState(errorMessage = "Error al obtener el libro")
                }
            } catch (e: Exception) {
                _uiState.value = EditBookUiState(errorMessage = "Error al conectar con la API")
            }
        }
    }


    fun actualizarLibro(book: Book) {
        viewModelScope.launch {
            try {
                val response = repository.modificarLibro(book.libroId, book) // Usamos modificarLibro y pasamos el id
                if (response.isSuccessful) {
                    _uiState.value = EditBookUiState(isSuccess = true)
                } else {
                    _uiState.value = EditBookUiState(errorMessage = "Error al actualizar libro")
                }
            } catch (e: Exception) {
                _uiState.value = EditBookUiState(errorMessage = "Error al conectar con la API")
            }
        }
    }

}

data class EditBookUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val book: Book? = null
)
