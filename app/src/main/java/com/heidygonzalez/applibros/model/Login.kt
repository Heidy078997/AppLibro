package com.heidygonzalez.applibros.model

data class LoginRequest(
    val Correo: String = "", // Correo del usuario
    val Pass: String = ""    // Contraseña del usuario
)

/*
data class LoginResponse(
    val mensaje: String,   // Mensaje de éxito o error (por ejemplo, "Inicio de sesión exitoso")
    val usuario: String    // Información del usuario o nombre de usuario
)*/