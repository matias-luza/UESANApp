package com.example.uesanapp.presentation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.uesanapp.data.local.db.AppDatabase
import com.example.uesanapp.data.model.CountryModel
import com.example.uesanapp.data.repository.CountryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CountryViewModel(private val repository: CountryRepository) : ViewModel() {

    val countriesList = listOf(
        CountryModel("Colombia", 5, "https://flagcdn.com/w320/co.png"),
        CountryModel("Francia", 3, "https://flagcdn.com/w320/fr.png"),
        CountryModel("Brasil", 8, "https://flagcdn.com/w320/br.png"),
        CountryModel("España", 2, "https://flagcdn.com/w320/es.png"),
        CountryModel("Portugal", 7, "https://flagcdn.com/w320/pt.png"),
        CountryModel("Argentina", 1, "https://flagcdn.com/w320/ar.png"),
        CountryModel("Japon", 10, "https://flagcdn.com/w320/jp.png"),
        CountryModel("Peru", 50, "https://flagcdn.com/w320/pe.png")
    )

    val favoriteCountries: StateFlow<List<CountryModel>> = repository.allFavorites
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val favoriteNames: StateFlow<List<String>> = repository.favoriteNames
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun toggleFavorite(country: CountryModel) {
        viewModelScope.launch {
            repository.toggleFavorite(country)
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CountryViewModel::class.java)) {
                val db = AppDatabase.getDatabase(application)
                val repository = CountryRepository(db.favoriteCountryDao())
                @Suppress("UNCHECKED_CAST")
                return CountryViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
