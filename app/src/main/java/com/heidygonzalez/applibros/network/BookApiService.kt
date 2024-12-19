package com.heidygonzalez.applibros.network
import com.heidygonzalez.applibros.model.Autor
import com.heidygonzalez.applibros.model.Book
import com.heidygonzalez.applibros.model.Genero
import com.heidygonzalez.applibros.model.LoginRequest

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApiService {
    @GET("Libro/buscar")
    suspend fun buscarLibro(@Query("titulo") titulo: String): Response<List<Book>>


    // Método para obtener todos los libros
    @GET("Libro")
    suspend fun obtenerTodosLibros(): Response<List<Book>>

    //metodo para obtener libro por id
    @GET("libro/{id}")
    suspend fun getLibro(@Path("id") id: Int): Response<Book>

//Método para obterner autores y generos
    @GET("Autor")
    suspend fun obtenerAutor(): List<Autor>

    @GET("Genero")
    suspend fun obtenerGenero(): List<Genero>

    //metodo agregar libro

    @POST("Libro")
    suspend fun agregarLibro(@Body book: Book): Response<Book>

    //metodo para eliminar libro

    @DELETE("Libro/{id}")
    suspend fun eliminarLibro(@Path("id") id: Int): Response<Void>

    // Método para modificar un libro
    @PUT("Libro/{id}")
    suspend fun modificarLibro(@Path("id") id: Int, @Body book: Book): Response<Book>

    //metodos por id

    @GET("Autor/{id}") // Nuevo método para obtener autor por ID
    suspend fun obtenerAutorPorId(@Path("id") id: Int): Response<Autor> // Cambié el endpoint para obtener por ID

    @GET("Genero/{id}") // Nuevo método para obtener género por ID
    suspend fun obtenerGeneroPorId(@Path("id") id: Int): Response<Genero> // Cambié el endpoint para obtener por ID


    //agregar autor

    @POST("Autor")
    suspend fun agregarAutor(@Body autor: Autor): Response<Autor>

    //Login
    @POST("Login/login")
    suspend fun iniciarSesion(@Body loginRequest: LoginRequest): Response<Void>






}