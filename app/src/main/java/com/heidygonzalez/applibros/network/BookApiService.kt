package com.heidygonzalez.applibros.network

import com.heidygonzalez.applibros.model.Autor
import com.heidygonzalez.applibros.model.Book
import com.heidygonzalez.applibros.model.Genero
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BookApiService {
    @GET("Libro/buscar")
    suspend fun buscarLibro(@Query("titulo") titulo: String): Response<List<Book>>


    // Método para obtener todos los libros
    @GET("Libro")
    suspend fun obtenerTodosLibros(): Response<List<Book>>

//Método para obterner autores y generos
    @GET("Autor")
    suspend fun obtenerAutor(): List<Autor>

    @GET("Genero")
    suspend fun obtenerGenero(): List<Genero>

    //metodo agregar libro

    @POST("Libro")
    suspend fun agregarLibro(@Body book: Book): Response<Book>
}