package com.example.movielister.ui.theme.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movielister.model.NowPlaying
import com.example.movielister.model.PopularMovie
import com.example.movielister.model.TopRated
import com.example.movielister.navigation.Movie_enum
import com.example.movielister.navigation.SharedViewModel

@Composable
fun ResultScreen(
    navController: NavController,
    popularMovie: PopularMovie? = null,
    topRatedMovie: TopRated? = null,
    nowPlayingMovie: NowPlaying? = null,
    modifier: Modifier = Modifier,
    sharedViewModel: SharedViewModel
) {
    val movieList = when {
        popularMovie != null -> popularMovie.results
        topRatedMovie != null -> topRatedMovie.results
        nowPlayingMovie != null -> nowPlayingMovie.results
        else -> emptyList()
    }

    // Create a state for the horizontal scroll position
    var scrollPosition by remember { mutableStateOf(0f) }

    // Animate the scroll position to create a looping effect
    val animatedScrollPosition by animateFloatAsState(
        targetValue = scrollPosition + 500f, // Adjust the target value for the scrolling speed
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000), // Adjust the duration for the scrolling speed
            repeatMode = RepeatMode.Restart
        )
    )
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        when {
            popularMovie != null -> {
                items(popularMovie.results) { movie ->
                    MovieCard(movie=movie, modifier = Modifier.padding(8.dp)) { movie ->
//                        navController.currentBackStackEntry?.savedStateHandle?.set(
//                            key="movie",
//                            value=movie
//                        )
                        sharedViewModel.addMovie(movie)
                        navController.navigate(Movie_enum.DetailsScreen.name )
                    }
                }
            }
            topRatedMovie != null -> {
                items(topRatedMovie.results) { movie ->
                    MovieCard(movie=movie, modifier = Modifier.padding(8.dp)) { movie ->
                        sharedViewModel.addMovie(movie)
                        navController.navigate(Movie_enum.DetailsScreen.name )
                    }
                }

            }
            nowPlayingMovie != null -> {
                items(nowPlayingMovie.results) { movie ->
                    MovieCard(movie=movie, modifier = Modifier.padding(8.dp)) { movie ->
                        sharedViewModel.addMovie(movie)
                        navController.navigate(Movie_enum.DetailsScreen.name)
                    }
                }
            }
        }
    }
}

