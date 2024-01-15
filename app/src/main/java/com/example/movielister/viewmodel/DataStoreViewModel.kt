package com.example.movielister.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.movielister.data.local.DataStoreManager
import com.example.movielister.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataStoreViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStore = DataStoreManager(application)

    // Use LiveData to observe changes in the watch list
    private val _watchList = MutableLiveData<List<Result>>()
    val watchList: LiveData<List<Result>> get() = _watchList

    // Function to fetch the watch list
    fun fetchWatchList() {
        viewModelScope.launch(Dispatchers.IO) {
            val getWatchList = dataStore.getWatchList()
            _watchList.postValue(getWatchList)
        }
    }

    // Function to add a movie to the watch list
    fun addToWatchList(movie: Result) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.addToWatchList(movie)
            fetchWatchList() // Update the watch list after adding a movie
        }
    }

    // Function to remove a movie from the watch list
//    fun removeFromWatchList(movie: Result) {
//        viewModelScope.launch(Dispatchers.IO) {
//            dataStore.removeFromWatchList(movie)
//            fetchWatchList() // Update the watch list after removing a movie
//        }
//    }
}
