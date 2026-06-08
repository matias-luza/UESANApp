package com.example.uesanapp.data.repository

import com.example.uesanapp.data.local.dao.FavoriteCountryDao
import com.example.uesanapp.data.local.entity.FavoriteCountryEntity
import com.example.uesanapp.data.model.CountryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CountryRepository(private val favoriteCountryDao: FavoriteCountryDao) {

    val allFavorites: Flow<List<CountryModel>> = favoriteCountryDao.getAllFavorites()
        .map { entities ->
            entities.map { it.toCountryModel() }
        }

    val favoriteNames: Flow<List<String>> = favoriteCountryDao.getFavoriteNames()

    suspend fun toggleFavorite(country: CountryModel) {
        val isFav = favoriteCountryDao.isFavorite(country.name)
        if (isFav) {
            favoriteCountryDao.deleteFavorite(FavoriteCountryEntity.fromCountryModel(country))
        } else {
            favoriteCountryDao.insertFavorite(FavoriteCountryEntity.fromCountryModel(country))
        }
    }

    suspend fun removeFavorite(country: CountryModel) {
        favoriteCountryDao.deleteFavorite(FavoriteCountryEntity.fromCountryModel(country))
    }
}
