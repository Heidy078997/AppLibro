package com.heidygonzalez.applibros.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.heidygonzalez.applibros.data.BookRepository

class ABookViewModelFactory {

    class ABookViewModelFactory(
        private val repository: BookRepository
    ) : ViewModelProvider.Factory {
        // Necesitamos anular el método create para proporcionar el ViewModel con el parámetro 'repository'
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            // Verificamos si el ViewModel solicitado es ABookViewModel
            if (modelClass.isAssignableFrom(ABookViewModel::class.java)) {
                // Si es así, creamos el ABookViewModel pasando el repository como parámetro
                @Suppress("UNCHECKED_CAST")
                return ABookViewModel(repository) as T
            }
            // Si el ViewModel no es ABookViewModel, lanzamos una excepción
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}