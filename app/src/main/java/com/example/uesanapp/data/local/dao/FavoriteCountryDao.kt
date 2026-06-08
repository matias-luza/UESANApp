package com.example.uesanapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.uesanapp.data.local.entity.FavoriteCountryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCountryDao {

    @Query("SELECT * FROM favorite_countries ORDER BY ranking ASC")
    fun getAllFavorites(): Flow<List<FavoriteCountryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(country: FavoriteCountryEntity)

    @Delete
    suspend fun deleteFavorite(country: FavoriteCountryEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_countries WHERE name = :name)")
    suspend fun isFavorite(name: String): Boolean

    @Query("SELECT name FROM favorite_countries")
    fun getFavoriteNames(): Flow<List<String>>
}
