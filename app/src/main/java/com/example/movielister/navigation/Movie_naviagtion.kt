package com.example.movielister.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movielister.model.Result
import com.example.movielister.ui.theme.screens.DetailsScreen
import com.example.movielister.ui.theme.screens.HomeScreen
import com.example.movielister.ui.theme.screens.MovieViewModel


@Composable
fun MovieNavigation() {
    val navController = rememberNavController()
    val movieViewModel: MovieViewModel = viewModel()

    val sharedViewModel:SharedViewModel= viewModel()

    NavHost(navController = navController, startDestination = Movie_enum.HomeScreen.name) {
        composable(Movie_enum.HomeScreen.name) {
            HomeScreen(
                navController = navController,
                PopularmovieUiState = movieViewModel.popularmovie_UiState,
                TopRatedmovieUiState = movieViewModel.topratedmovie_UiState,
                NowPlayingmovieUiState = movieViewModel.nowplayingmovie_UiState,
                sharedViewModel=sharedViewModel
            )
        }

//        composable("${Movie_enum.DetailsScreen.name}/{movie}",
//            arguments = listOf(navArgument("movie") { type = NavType.ParcelableType(Result::class.java) })
//        ) { backStackEntry ->
//            val movie = backStackEntry.arguments?.getParcelable<Result>("movie")
//
//            if (movie != null) {
//                DetailsScreen(navController, movie)
//            } else {
//                // Handle error or loading state
//            }
//        }

        composable(route=Movie_enum.DetailsScreen.name){
            val result=
                navController.previousBackStackEntry?.savedStateHandle?.get<Result>("movie")
            DetailsScreen(navController = navController, sharedViewModel=sharedViewModel )
        }
    }
}

