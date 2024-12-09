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
}
