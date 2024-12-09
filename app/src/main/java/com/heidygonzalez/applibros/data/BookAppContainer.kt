package com.heidygonzalez.applibros.data

import com.heidygonzalez.applibros.network.BookApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*

object BookAppContainer {
    private const val BASE_URL = "https://10.0.2.2:7039/api/" // URL base de la API

    val bookApi: BookApiService by lazy {
        // Crear un TrustManager personalizado que acepte todos los certificados
        val trustManager = object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        }

        // Configurar SSLContext con el TrustManager personalizado
        val sslContext = SSLContext.getInstance("TLS").apply {
            init(null, arrayOf(trustManager), null)
        }

        // Configurar OkHttpClient con sslSocketFactory y hostnameVerifier
        val client = OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustManager) // Aquí se usa el TrustManager correcto
            .hostnameVerifier { _, _ -> true } // Deshabilitar verificación de hostname (solo pruebas)

            // Agregar un interceptor para logging (opcional, para depuración)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        // Crear instancia de Retrofit con OkHttpClient personalizado
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookApiService::class.java)
    }
}
