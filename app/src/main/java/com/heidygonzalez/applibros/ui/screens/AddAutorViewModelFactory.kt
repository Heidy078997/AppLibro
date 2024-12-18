package com.heidygonzalez.applibros.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


import com.heidygonzalez.applibros.data.BookRepository

class AddAutorViewModelFactory(private val repository: BookRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddAutorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddAutorViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
