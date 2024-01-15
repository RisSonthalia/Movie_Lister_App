package com.example.movielister.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movielister.model.Result
import com.example.movielister.viewmodel.DataStoreViewModel
import com.example.movielister.viewmodel.BottomNavigationViewModel
import com.example.movielister.ui.theme.screens.DetailsScreen
import com.example.movielister.ui.theme.screens.HomeScreen
import com.example.movielister.viewmodel.MovieViewModel
import com.example.movielister.ui.theme.screens.SearchScreen
//import com.example.movielister.ui.theme.screens.SearchScreen
import com.example.movielister.ui.theme.screens.WatchListScreen
import com.example.movielister.viewmodel.WatchListViewModel


@Composable
fun MovieNavigation() {
    val navController = rememberNavController()
    val movieViewModel: MovieViewModel = viewModel()

    val sharedViewModel:SharedViewModel= viewModel()
    val watchListViewModel: WatchListViewModel = viewModel()
    val bottomNavigationViewModel: BottomNavigationViewModel = viewModel()
    val dataStoreViewModel: DataStoreViewModel = viewModel()

    NavHost(navController = navController, startDestination = Movie_enum.HomeScreen.name) {
        composable(Movie_enum.HomeScreen.name) {
            HomeScreen(
                navController = navController,
                PopularmovieUiState = movieViewModel.popularmovie_UiState,
                TopRatedmovieUiState = movieViewModel.topratedmovie_UiState,
                NowPlayingmovieUiState = movieViewModel.nowplayingmovie_UiState,
                sharedViewModel=sharedViewModel,
                bottomNavigationViewModel = bottomNavigationViewModel
            )
        }
        composable(route=Movie_enum.DetailsScreen.name){
            val result=
                navController.previousBackStackEntry?.savedStateHandle?.get<Result>("movie")
            DetailsScreen(navController = navController, sharedViewModel=sharedViewModel ,watchListViewModel=watchListViewModel, dataStoreViewModel = dataStoreViewModel)
        }

        composable(Movie_enum.WatchListScreen.name) {
            WatchListScreen(
                navController = navController,
                watchListViewModel=watchListViewModel,
                sharedViewModel=sharedViewModel,
                bottomNavigationViewModel = bottomNavigationViewModel,
                dataStoreViewModel=dataStoreViewModel
            )
        }

        composable(Movie_enum.SearchScreen.name) {
            SearchScreen(
                navController = navController,
                watchListViewModel=watchListViewModel,
                sharedViewModel=sharedViewModel,
                bottomNavigationViewModel = bottomNavigationViewModel
            )
        }
    }
}

