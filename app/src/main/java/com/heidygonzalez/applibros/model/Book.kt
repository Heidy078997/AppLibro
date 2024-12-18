package com.heidygonzalez.applibros.model


data class Book(
    val libroId: Int,
    val titulo: String,
    val isbn: String,
    val anhoPublicacion: Int,
    val generoId: Int,
    val autorId: Int,
    val paginas: Int,
    val portadaUrl: String?,
    val puntuacion: Double,
    val fechaRegistro: String,
    val sinopsis: String,
    val autor: Autor,
    val genero: Genero

)

data class Autor(
    val autorId: Int,
    val nombre: String,
)

data class Genero(
    val generoId: Int,
    val descripcion: String,

)