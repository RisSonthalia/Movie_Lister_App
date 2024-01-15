package com.example.movielister.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.movielister.model.Result
import androidx.compose.runtime.State

class WatchListViewModel : ViewModel() {

    private val _watchList = mutableStateOf<List<Result>>(emptyList())
    val watchList: State<List<Result>> get()= _watchList

    fun addToWatchList(movie: Result) {
        _watchList.value = _watchList.value + movie
    }

    fun removeFromWatchList(movie: Result) {
        val updatedList = _watchList.value.toMutableList()
        updatedList.remove(movie)
        _watchList.value = updatedList
    }

    fun getCount(movie: Result): Int {
        return _watchList.value.count { it == movie }
    }

    fun isempty():Boolean{
       return _watchList.value.isEmpty()
    }


}


