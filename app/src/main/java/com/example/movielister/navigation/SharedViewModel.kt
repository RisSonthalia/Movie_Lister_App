package com.example.movielister.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.movielister.model.Result

class SharedViewModel:ViewModel() {

    var movie by mutableStateOf<Result?>(null)
        private set

    fun addMovie(newmovie:Result){
        movie=newmovie

    }
}