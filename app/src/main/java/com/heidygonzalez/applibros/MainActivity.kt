package com.heidygonzalez.applibros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.heidygonzalez.applibros.data.BookAppContainer
import com.heidygonzalez.applibros.data.BookRepository
import com.heidygonzalez.applibros.ui.screens.ABookViewModel
import com.heidygonzalez.applibros.ui.screens.ABookViewModelFactory
import com.heidygonzalez.applibros.ui.screens.AddBookScreen
import com.heidygonzalez.applibros.ui.screens.BookDetailScreen
import com.heidygonzalez.applibros.ui.screens.BookViewModelFactory
import com.heidygonzalez.applibros.ui.screens.SearchScreen
import com.heidygonzalez.applibros.ui.screens.SearchViewModel
import com.heidygonzalez.applibros.ui.screens.SearchViewModelFactory
import com.heidygonzalez.applibros.ui.theme.AppLibrosTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Inicializamos el NavController para la navegación
            val navController = rememberNavController()

            // Inicializamos el repository y viewModel para la pantalla de búsqueda
            val repository = BookRepository(BookAppContainer.bookApi)
            val searchViewModel = ViewModelProvider(this, SearchViewModelFactory(repository))
                .get(SearchViewModel::class.java)

            // Inicializamos el ViewModel para agregar un libro
            val addBookViewModel = ViewModelProvider(this,
                ABookViewModelFactory.ABookViewModelFactory(repository)
            )
                .get(ABookViewModel::class.java)

            // Definir la navegación
            NavHost(navController = navController, startDestination = "searchScreen") {
                composable("searchScreen") {
                    // Aquí mostramos la pantalla de búsqueda
                    SearchScreen(viewModel = searchViewModel, navController = navController)
                }
                composable("addBookScreen") {
                    // Y aquí mostramos la pantalla para agregar un libro
                    AddBookScreen(viewModel = addBookViewModel, navController = navController)
                }
                composable("bookDetailScreen/{bookId}") { backStackEntry ->
                    // Obtener el ID del libro desde los argumentos de la navegación
                    val bookId = backStackEntry.arguments?.getString("bookId") ?: return@composable
                    // Aquí mostramos la pantalla de detalles del libro
                    BookDetailScreen(bookId = bookId, navController = navController, viewModel = searchViewModel)
                }
            }
        }
    }
}
