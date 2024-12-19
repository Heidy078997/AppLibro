package com.heidygonzalez.applibros.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
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

    //se agrega

    private val _selectedBook = MutableStateFlow<Book?>(null)
    val selectedBook: StateFlow<Book?> = _selectedBook

    //hasta aca

    init {
        cargarLibros()
    }

    fun cargarLibros() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val books = repository.obtenerTodosLibros()
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
        cargarLibros()
    }

    fun eliminarLibro(id: Int, context: Context) {
        viewModelScope.launch {
            try {
                val response = repository.eliminarLibro(id)
                if (response.isSuccessful) {
                    val librosActualizados = _uiState.value.books.filterNot { it.libroId == id }
                    _uiState.value = _uiState.value.copy(books = librosActualizados)

                    Toast.makeText(context, "Libro eliminado correctamente.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Error al eliminar el libro. Intenta nuevamente.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}


data class SearchUiState(
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)