package com.heidygonzalez.applibros.ui.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heidygonzalez.applibros.data.BookRepository
import com.heidygonzalez.applibros.model.Autor

import kotlinx.coroutines.launch
import retrofit2.Response

class AddAutorViewModel(private val repository: BookRepository) : ViewModel() {

    private val _autores = MutableLiveData<List<Autor>>()
    //val autores: LiveData<List<Autor>> get() = _autores

    //var resultadoAgregarAutor: Response<Autor>? = null
    var error: String? = null

    fun agregarAutor(autor: Autor, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.agregarAutor(autor)  // Llama al repositorio para agregar el autor
                if (response.isSuccessful) {
                    // Si el autor se agrega correctamente, recargamos la lista de autores
                    cargarAutores()  // Esta función recarga la lista de autores desde el repositorio
                    onResult(true)  // Notificar éxito
                } else {
                    onResult(false)  // Notificar error
                }
            } catch (e: Exception) {
                Log.e("AddAutorViewModel", "Excepción al agregar autor: ${e.message}")
                onResult(false)  // Notificar error en caso de excepción
            }
        }
    }

    // Función para cargar la lista de autores
    private fun cargarAutores() {
        viewModelScope.launch {
            try {
                val autoresList = repository.obtenerAutor()  // Llama al repositorio para obtener la lista de autores
                _autores.postValue(autoresList)  // Actualiza la lista de autores en el LiveData
            } catch (e: Exception) {
                Log.e("AddAutorViewModel", "Excepción al cargar autores: ${e.message}")
            }
        }
    }
}
