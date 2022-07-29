package com.dicoding.mycatapplication.core.domain

import com.dicoding.mycatapplication.core.data.local.database.BreedEntity

data class BreedDomain(
    val id: Int = 0,
    val breedName: String,
    val country: String,
    val origin: String,
    val pattern: String,
    val isFavorite: Boolean = false
)

fun BreedEntity.changeIntoDomainModel(): BreedDomain {
    return BreedDomain(
        id,
        breedName,
        country,
        origin,
        pattern,
        favorite
    )
}

fun BreedDomain.changeIntoEntityModel(): BreedEntity {
    return BreedEntity(
        id,
        breedName,
        country,
        origin,
        pattern,
        favorite = isFavorite
    )
}