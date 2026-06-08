package com.example.uesanapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.uesanapp.data.model.CountryModel

@Entity(tableName = "favorite_countries")
data class FavoriteCountryEntity(
    @PrimaryKey val name: String,
    val ranking: Int,
    val imageUrl: String
) {
    fun toCountryModel(): CountryModel = CountryModel(
        name = name,
        ranking = ranking,
        imageUrl = imageUrl
    )

    companion object {
        fun fromCountryModel(country: CountryModel): FavoriteCountryEntity = FavoriteCountryEntity(
            name = country.name,
            ranking = country.ranking,
            imageUrl = country.imageUrl
        )
    }
}
