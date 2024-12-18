package com.heidygonzalez.applibros.data


import android.util.Log
import com.heidygonzalez.applibros.model.Autor
import com.heidygonzalez.applibros.model.Book
import com.heidygonzalez.applibros.model.Genero
import com.heidygonzalez.applibros.network.BookApiService
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

import okhttp3.ResponseBody


class BookRepository(private val bookApi: BookApiService) {

    suspend fun obtenerTodosLibros(): List<Book> {
        val response = bookApi.obtenerTodosLibros()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        }
        return emptyList()
    }

    suspend fun buscarLibros(titulo: String): List<Book> {
        return try {
            // Hacer la llamada a la API
            val response = bookApi.buscarLibro(titulo)

            // Loguear la respuesta de la API
            Log.d("BookRepository", "Respuesta de la API: $response")

            if (response.isSuccessful) {
                response.body() ?: emptyList() // Devuelve los libros o una lista vacía si el body es nulo
            } else {
                Log.e("BookRepository", "Error HTTP: ${response.code()} - ${response.message()}")
                emptyList()
            }
        } catch (e: HttpException) {
            // Manejo de errores HTTP específicos
            Log.e("BookRepository", "Error HTTP: ${e.code()} - ${e.message()}")
            emptyList() // Devuelve una lista vacía en caso de error
        } catch (e: SocketTimeoutException) {
            // Manejo de tiempo de espera
            Log.e("BookRepository", "Error: Tiempo de espera agotado")
            emptyList()
        } catch (e: UnknownHostException) {
            // Manejo de falta de conexión a Internet
            Log.e("BookRepository", "Error: Sin conexión a Internet")
            emptyList()
        } catch (e: IOException) {
            // Otros errores relacionados con la red
            Log.e("BookRepository", "Error de red: ${e.message}")
            emptyList()
        } catch (e: Exception) {
            // Excepción genérica como último recurso
            Log.e("BookRepository", "Error desconocido: ${e.message}")
            emptyList()
        }
    }

    //nuevo codigo desde aca

    // Obtener autores disponibles
    suspend fun obtenerAutor(): List<Autor> {
        return bookApi.obtenerAutor()
    }

    // Obtener géneros disponibles
    suspend fun obtenerGenero(): List<Genero> {
        return bookApi.obtenerGenero()
    }

    // Agregar un nuevo libro
    suspend fun agregarLibro(book: Book): Response<Book> {
        return bookApi.agregarLibro(book)
    }

    //hasta aca

    suspend fun eliminarLibro(id: Int): Response<Void> {
        return bookApi.eliminarLibro(id)
    }

    suspend fun modificarLibro(id: Int, book: Book): Response<Book> {
        return try {
            bookApi.modificarLibro(id, book) // Llamada a la API
        } catch (e: Exception) {
            Log.e("BookRepository", "Error modificando el libro: ${e.message}")
            throw e
        }
    }



    // Nuevo método para obtener un libro por ID
    suspend fun obtenerLibroPorId(id: Int): Response<Book> {
        return try {
            // Realiza la solicitud GET al endpoint correspondiente en la API
            bookApi.getLibro(id)
        } catch (e: HttpException) {
            // En caso de un error HTTP, se devuelve una respuesta de error con el código HTTP y mensaje
            val errorBody = ResponseBody.create(null, "Error en la conexión: ${e.message()}")
            Response.error<Book>(e.code(), errorBody)
        } catch (e: Exception) {
            // En caso de otro tipo de excepción, se devuelve una respuesta de error genérica
            val errorBody = ResponseBody.create(null, "Error desconocido: ${e.message}")
            Response.error<Book>(500, errorBody)
        }
    }

    //nuevos metodos

    // Nuevo método para obtener autor por id
    suspend fun obtenerAutorPorId(id: Int): Response<Autor> {
        return try {
            bookApi.obtenerAutorPorId(id)
        } catch (e: Exception) {
            val errorBody = ResponseBody.create(null, "Error en la conexión: ${e.message}")
            Response.error<Autor>(500, errorBody)
        }
    }

    // Nuevo método para obtener genero por id
    suspend fun obtenerGeneroPorId(id: Int): Response<Genero> {
        return try {
            bookApi.obtenerGeneroPorId(id)
        } catch (e: Exception) {
            val errorBody = ResponseBody.create(null, "Error en la conexión: ${e.message}")
            Response.error<Genero>(500, errorBody)
        }
    }

    suspend fun agregarAutor(autor: Autor): Response<Autor> {
        return try {
            bookApi.agregarAutor(autor) // Llamada a la API
        } catch (e: Exception) {
            Log.e("BookRepository", "Error al agregar el autor: ${e.message}")
            throw e
        }
    }



}
