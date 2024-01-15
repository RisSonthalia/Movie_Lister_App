package com.example.movielister.ui.theme.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movielister.navigation.SharedViewModel
import com.example.movielister.viewmodel.NowPlaying_UiState
import com.example.movielister.viewmodel.PopularMovie_UiState
import com.example.movielister.viewmodel.TopRatedMovie_UiState


@Composable
fun MovieCategory(navController: NavController, category: String, movieUiState: Any,sharedViewModel: SharedViewModel) {
    Column {
        Text(
            text = category,
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                fontSize = 20.sp
            )
        )

        when (movieUiState) {
            is PopularMovie_UiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxWidth())
            is TopRatedMovie_UiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxWidth())
            is NowPlaying_UiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxWidth())

            is PopularMovie_UiState.Success -> ResultScreen(
                navController = navController,
                popularMovie = movieUiState.PopularMovie,
                modifier = Modifier.fillMaxWidth(),
                sharedViewModel = sharedViewModel
            )

            is TopRatedMovie_UiState.Success -> ResultScreen(
                navController = navController,
                topRatedMovie = movieUiState.TopRatedMovie,
                modifier = Modifier.fillMaxWidth(),
                sharedViewModel = sharedViewModel
            )

            is NowPlaying_UiState.Success -> ResultScreen(
                navController = navController,
                nowPlayingMovie = movieUiState.NowPlayingMovie,
                modifier = Modifier.fillMaxWidth(),
                sharedViewModel = sharedViewModel
            )

            is PopularMovie_UiState.Error -> ErrorScreen(modifier = Modifier.fillMaxWidth())
            is TopRatedMovie_UiState.Error -> ErrorScreen(modifier = Modifier.fillMaxWidth())
            is NowPlaying_UiState.Error -> ErrorScreen(modifier = Modifier.fillMaxWidth())
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}


