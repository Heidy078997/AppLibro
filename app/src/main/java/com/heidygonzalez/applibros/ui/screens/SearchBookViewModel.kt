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

    init {
        cargarLibros()
    }

    private fun cargarLibros() {
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

    // Llamada para modificar un libro
    fun modificarLibro(book: Book) {
        viewModelScope.launch {
            try {
                // Usamos 'bookRepository' que es la instancia de BookRepository
                val response = repository.modificarLibro(book.libroId, book)
                if (response.isSuccessful) {
                    // Aquí puedes manejar la respuesta en caso de éxito
                    Log.d("SearchViewModel", "Libro modificado correctamente: ${response.body()}")
                } else {
                    // Manejo de error en caso de que la respuesta no sea exitosa
                    Log.e("SearchViewModel", "Error al modificar el libro: ${response.message()}")
                }
            } catch (e: Exception) {
                // Manejo de excepciones
                Log.e("SearchViewModel", "Error desconocido al modificar el libro: ${e.message}")
            }
        }
    }
}


data class SearchUiState(
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)