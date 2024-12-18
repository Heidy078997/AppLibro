package com.heidygonzalez.applibros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.heidygonzalez.applibros.data.BookAppContainer
import com.heidygonzalez.applibros.data.BookRepository
import com.heidygonzalez.applibros.ui.screens.ABookViewModel
import com.heidygonzalez.applibros.ui.screens.ABookViewModelFactory
import com.heidygonzalez.applibros.ui.screens.AddAutorScreen
import com.heidygonzalez.applibros.ui.screens.AddAutorViewModel
import com.heidygonzalez.applibros.ui.screens.AddAutorViewModelFactory
import com.heidygonzalez.applibros.ui.screens.AddBookScreen
import com.heidygonzalez.applibros.ui.screens.BookDetailScreen
import com.heidygonzalez.applibros.ui.screens.EditBookScreen
import com.heidygonzalez.applibros.ui.screens.EditBookViewModel
import com.heidygonzalez.applibros.ui.screens.EditBookViewModelFactory
import com.heidygonzalez.applibros.ui.screens.SearchScreen
import com.heidygonzalez.applibros.ui.screens.SearchViewModel
import com.heidygonzalez.applibros.ui.screens.SearchViewModelFactory
import com.heidygonzalez.applibros.ui.screens.SharedBookViewModel
import com.heidygonzalez.applibros.ui.screens.SharedBookViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Inicializamos el NavController para la navegación
            val navController = rememberNavController()

            //se crea de momento esto
            val sharedBookViewModel: SharedBookViewModel = viewModel(factory = SharedBookViewModelFactory(BookRepository(BookAppContainer.bookApi)))

            // Inicializamos el repository
            val repository = BookRepository(BookAppContainer.bookApi)

            // Inicializamos los ViewModels que ya tienes
            val searchViewModel = ViewModelProvider(this, SearchViewModelFactory(repository))
                .get(SearchViewModel::class.java)

            val addBookViewModel = ViewModelProvider(
                this, ABookViewModelFactory.ABookViewModelFactory(repository)
            ).get(ABookViewModel::class.java)

            // Nuevo: Inicializamos el ViewModel para EditBookScreen
            val editBookViewModel = ViewModelProvider(this, EditBookViewModelFactory(repository))
                .get(EditBookViewModel::class.java)

            //se inicializa aqui

            // Nuevo: Inicializamos el ViewModel para AddAutorScreen
            val addAutorViewModel = ViewModelProvider(this, AddAutorViewModelFactory(repository))
                .get(AddAutorViewModel::class.java)

            //hasta aqui

            // Definir la navegación
            NavHost(navController = navController, startDestination = "searchScreen") {
                composable("searchScreen") {
                    // Aquí mostramos la pantalla de búsqueda
                    SearchScreen(viewModel = searchViewModel, navController = navController, sharedViewModel = sharedBookViewModel)
                }
                composable("addBookScreen") {
                    // Y aquí mostramos la pantalla para agregar un libro
                    AddBookScreen(viewModel = addBookViewModel, navController = navController)
                }
                composable("bookDetailScreen/{bookId}") { backStackEntry ->
                    // Obtener el ID del libro desde los argumentos de la navegación
                    val bookId = backStackEntry.arguments?.getString("bookId") ?: return@composable
                    // Aquí mostramos la pantalla de detalles del libro
                    BookDetailScreen(
                        bookId = bookId,
                        navController = navController,
                        viewModel = searchViewModel,
                        sharedViewModel = sharedBookViewModel
                    )
                }

                composable("editBook/{bookId}") { backStackEntry ->
                    val bookId = backStackEntry.arguments?.getString("bookId")?.toIntOrNull()
                    if (bookId != null) {
                        // Pasamos el ViewModel y navController a EditBookScreen
                        EditBookScreen(
                            bookId = bookId,
                            viewModel = editBookViewModel,
                            navController = navController,
                            sharedViewModel = sharedBookViewModel
                        )
                    }
                }

                //nuevo
                composable("addAutorScreen") {
                    AddAutorScreen(viewModel = addAutorViewModel, navController = navController)
                }

            }
        }
    }
}