package com.heidygonzalez.applibros.ui.screens

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heidygonzalez.applibros.data.BookRepository
import com.heidygonzalez.applibros.model.LoginRequest
import kotlinx.coroutines.launch

class LoginViewModel(private val bookRepository: BookRepository) : ViewModel() {

    private val _loginState = MutableLiveData<Boolean>()
    val loginState: LiveData<Boolean> get() = _loginState

    fun iniciarSesion(loginRequest: LoginRequest) {
        viewModelScope.launch {
            val loginExitoso = bookRepository.iniciarSesion(loginRequest)
            _loginState.postValue(loginExitoso)
            if (loginExitoso) {
                Log.d("LoginViewModel", "Inicio de sesión exitoso")
            } else {
                Log.e("LoginViewModel", "Error en el inicio de sesión")
            }
        }
    }

}

// Definir los estados de UI
sealed class UiState {
    object Loading : UiState() // Cuando se está cargando la respuesta
    data class Error(val errorMessage: String) : UiState() // Error al hacer la solicitud
}