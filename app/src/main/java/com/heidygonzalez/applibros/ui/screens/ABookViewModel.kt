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


class ABookViewModel(private val repository: BookRepository) : ViewModel() {
    private val _authors = MutableStateFlow<List<Autor>>(emptyList())
    val autores: StateFlow<List<Autor>> = _authors

    private val _genres = MutableStateFlow<List<Genero>>(emptyList())
    val generos: StateFlow<List<Genero>> = _genres

    private val _uiState = MutableStateFlow(AddBookUiState())
    val uiState: StateFlow<AddBookUiState> = _uiState

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

    fun agregarLibro(book: Book) {
        viewModelScope.launch {
            _uiState.value = AddBookUiState(isLoading = true)
            try {
                val response = repository.agregarLibro(book)
                if (response.isSuccessful) {
                    _uiState.value = AddBookUiState(isSuccess = true)
                } else {
                    _uiState.value = AddBookUiState(errorMessage = "Error al agregar libro")
                }
            } catch (e: Exception) {
                _uiState.value = AddBookUiState(errorMessage = "Error al conectar con la API")
            }
        }
    }

    fun clearSuccessMessage() {
        _uiState.value = _uiState.value.copy(isSuccess = false)
    }
}

data class AddBookUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)


