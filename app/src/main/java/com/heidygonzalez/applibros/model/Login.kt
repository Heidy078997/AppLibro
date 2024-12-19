package com.heidygonzalez.applibros.model

data class LoginRequest(
    val Correo: String = "", // Correo del usuario
    val Pass: String = ""    // Contraseña del usuario
)

