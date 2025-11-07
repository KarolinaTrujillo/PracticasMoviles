package com.karo.examu2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karo.examu2.data.datastore.ThemeDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ThemeViewModel(private val dataStore: ThemeDataStore) : ViewModel() {
    val darkMode: Flow<Boolean> = dataStore.isDarkMode

    fun setDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            dataStore.setDarkMode(enabled)
        }
    }
}
