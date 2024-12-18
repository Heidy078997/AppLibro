package com.heidygonzalez.applibros.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heidygonzalez.applibros.data.BookRepository
import com.heidygonzalez.applibros.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SharedBookViewModel(private val repository: BookRepository) : ViewModel() {
    // Usamos StateFlow para manejar el libro actualizado
    private val _updatedBook = MutableStateFlow<Book?>(null)
    val updatedBook: StateFlow<Book?> = _updatedBook

    // Llamado después de actualizar un libro
    fun notifyBookUpdated(book: Book) {
        viewModelScope.launch {
            _updatedBook.value = book
        }
    }
}
