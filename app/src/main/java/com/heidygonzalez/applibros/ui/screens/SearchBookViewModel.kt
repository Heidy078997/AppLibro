package com.heidygonzalez.applibros.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heidygonzalez.applibros.data.BookRepository
import com.heidygonzalez.applibros.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class SearchViewModel(private val repository: BookRepository) : ViewModel() {


    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    init {
        // Cargar todos los libros al inicio
        cargarLibros()
    }

    private fun cargarLibros() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val books = repository.obtenerTodosLibros()  // Método para obtener todos los libros
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                books = books,
                errorMessage = if (books.isEmpty()) "No se encontraron libros" else null
            )
        }

    }

    fun buscarLibros(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val books = repository.buscarLibros(query)
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                books = books,
                errorMessage = if (books.isEmpty()) "No se encontraron resultados" else null
            )
        }
    }

    fun clearSearch() {
        // Al borrar la búsqueda, volver a cargar todos los libros
        cargarLibros()
    }
}


data class SearchUiState(
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
