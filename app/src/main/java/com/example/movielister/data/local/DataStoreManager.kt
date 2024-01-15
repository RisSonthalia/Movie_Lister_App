package com.example.movielister.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.movielister.model.Result
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

//
//class DataStoreManager(context: Context) {
//
//    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "WatchList_Key")
//    private val dataStore = context.dataStore
//
//    companion object {
//        val WatchListKey = intPreferencesKey("watchlist_key")
//    }
//
//    object WatchListKeys {
//        val counter = intPreferencesKey ("counter")
//        val adult = booleanPreferencesKey("adult_$counter")
//        val backdropPath = stringPreferencesKey("backdropPath_$counter")
//        val id = intPreferencesKey("id")
//        val genreid= stringPreferencesKey("genre_id")
//        val originalLanguage = stringPreferencesKey("originalLanguage_$counter")
//        val originalTitle = stringPreferencesKey("originalTitle_$counter")
//        val overview = stringPreferencesKey("overview_$counter")
//        val popularity = doublePreferencesKey("popularity_$counter")
//        val posterPath = stringPreferencesKey("posterPath_$counter")
//        val releaseDate = stringPreferencesKey("releaseDate_$counter")
//        val title = stringPreferencesKey("title_$counter")
//        val video = booleanPreferencesKey("video_$counter")
//        val voteAverage = doublePreferencesKey("voteAverage_$counter")
//        val voteCount = intPreferencesKey("voteCount_$counter")
//    }
//
//    private val json = Json
//
//    suspend fun addToWatchList(movie: Result) {
//        var counter = dataStore.data.first()[WatchListKeys.counter] ?: 0
//
//        dataStore.edit { preferences ->
//
//            try {
//                preferences[WatchListKeys.counter] = counter + 1
//                preferences[WatchListKeys.adult] = movie.adult
//                preferences[WatchListKeys.backdropPath] = movie.backdrop_path
//                preferences[WatchListKeys.id] = movie.id
//                preferences[WatchListKeys.originalLanguage] = movie.original_language
//                preferences[WatchListKeys.originalTitle] = movie.original_title
//                preferences[WatchListKeys.overview] = movie.overview
//                preferences[WatchListKeys.popularity] = movie.popularity
//                preferences[WatchListKeys.posterPath] = movie.poster_path
//                preferences[WatchListKeys.releaseDate] = movie.release_date
//                preferences[WatchListKeys.title] = movie.title
//                preferences[WatchListKeys.video] = movie.video
//                preferences[WatchListKeys.voteAverage] = movie.vote_average
//                preferences[WatchListKeys.voteCount] = movie.vote_count
//
//            } catch (e: Exception) {
//                // Handle the encoding error, you can log it or take appropriate action.
//                // For example, you might want to remove the movie from the watchlist if encoding fails.
//                // Or you could rethrow the exception if you want to crash the app in case of encoding errors.
//
//                e.printStackTrace()
//            }
//        }
//    }
//
////    suspend fun removeFromWatchList(movie: Result) {
////        dataStore.edit { preferences ->
////            val currentWatchList = preferences[WatchListKey]?.toMutableSet() ?: mutableSetOf()
////            currentWatchList.remove(json.encodeToString(movie))
////            preferences[WatchListKey] = currentWatchList
////        }
////    }
//
//    suspend fun getWatchList(): MutableList<Result> {
//        return dataStore.data.first().let { preferences ->
//            val counter = preferences[WatchListKeys.counter] ?: 0
//            val watchList = mutableListOf<Result>()
//            val rem_counter=counter
//
//            for (i in 0..rem_counter) {
//                val movie = Result(
//                    adult = preferences[WatchListKeys.adult] ?: false,
//                    backdrop_path = preferences[WatchListKeys.backdropPath] ?: "",
//                    id = preferences[WatchListKeys.id] ?: 0,
//                    original_language = preferences[WatchListKeys.originalLanguage] ?: "",
//                    original_title = preferences[WatchListKeys.originalTitle] ?: "",
//                    overview = preferences[WatchListKeys.overview] ?: "",
//                    popularity = preferences[WatchListKeys.popularity] ?: 0.0,
//                    poster_path = preferences[WatchListKeys.posterPath] ?: "",
//                    release_date = preferences[WatchListKeys.releaseDate] ?: "",
//                    title = preferences[WatchListKeys.title] ?: "",
//                    video = preferences[WatchListKeys.video] ?: false,
//                    vote_average = preferences[WatchListKeys.voteAverage] ?: 0.0,
//                    vote_count = preferences[WatchListKeys.voteCount] ?:0,
//                    genre_ids = listOf(1,2)
//                )
//                watchList.add(movie)
//            }
//
//            watchList
//        }
//    }
//}
class DataStoreManager(context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "WatchList_Key")
    private val dataStore = context.dataStore


    companion object {
        val WatchListKey = intPreferencesKey("watchlist_key")
    }

    object WatchListKeys {
        val counter = intPreferencesKey("counter")

        fun dynamicKey(counterValue: Int): Preferences.Key<String> {
            return stringPreferencesKey("movie_$counterValue")
        }
    }

    private val json = Json

    suspend fun addToWatchList(movie: Result) {
        var counter = dataStore.data.first()[WatchListKeys.counter] ?: 0

        dataStore.edit { preferences ->
            try {
                val counterValue = counter + 1
                preferences[WatchListKeys.counter] = counterValue

                val dynamicKey = WatchListKeys.dynamicKey(counterValue)
                val movieJson = json.encodeToString(movie)
                preferences[dynamicKey] = movieJson
                Log.d("G","Successful addtowatchlist")


            } catch (e: Exception) {
                Log.d("G","Exception inside addtowatchlist")
                e.printStackTrace()
            }
        }
    }

    suspend fun getWatchList(): MutableList<Result> {
        return dataStore.data.first().let { preferences ->
            val counter = preferences[WatchListKeys.counter] ?: 0
            val watchList = mutableListOf<Result>()

            for (i in 1..counter) {
                val dynamicKey = WatchListKeys.dynamicKey(i)
                val movieJson = preferences[dynamicKey] ?: continue
                val movie = json.decodeFromString<Result>(movieJson)
                watchList.add(movie)
            }

            watchList
        }
    }
}

//class DataStoreManager(context: Context) {
//
//    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "WatchList_Key")
//    private val dataStore = context.dataStore
//
//    companion object {
//        object WatchListKeys {
//            val watchList = stringPreferencesKey("watchList")
//        }
//    }
//
//    private val json = Json{ ignoreUnknownKeys = true }
//
//    suspend fun addToWatchList(movie: Result) {
//        dataStore.edit { preferences ->
//            try {
//                val currentWatchListJson = preferences[WatchListKeys.watchList] ?: "[]"
//                // Log current watchlist JSON
//                Log.d("DataStoreManager", "Current Watchlist JSON: $currentWatchListJson")
//
//                val currentWatchList = json.decodeFromString<List<Result>>(currentWatchListJson)
//
//                val updatedWatchList = currentWatchList.toMutableList().apply {
//                    add(movie)
//                }
//
//                val updatedWatchListJson = json.encodeToString(updatedWatchList)
//                // Log updated watchlist JSON
//                Log.d("DataStoreManager", "Updated Watchlist JSON: $updatedWatchListJson")
//
//                preferences[WatchListKeys.watchList] = updatedWatchListJson
//            } catch (e: Exception) {
//                // Handle the encoding error as needed
//                Log.e("DataStoreManager", "Error adding movie to watchlist", e)
//            }
//        }
//    }
//
//    suspend fun getWatchList(): List<Result> {
//        return dataStore.data.first().let { preferences ->
//            val currentWatchListJson = preferences[WatchListKeys.watchList] ?: "[]"
//            return try {
//                json.decodeFromString(currentWatchListJson)
//            } catch (e: Exception) {
//                // Handle decoding error, return an empty list, or take appropriate action
//                Log.e("DataStoreManager", "Error decoding watchlist", e)
//                emptyList()
//            }
//        }
//    }
//}
