package com.example.movielister.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class BottomNavigationViewModel : ViewModel() {
    private var _selectedItemIndex = mutableStateOf(0)

    var selecteditemindex: Int
        get() = _selectedItemIndex.value
        private set(value) {
            _selectedItemIndex.value = value
        }

    fun setSelecteditemindexed(value: Int) {
        selecteditemindex = value
    }

    fun getSelecteditemindexed(): Int {
        return selecteditemindex
    }
}