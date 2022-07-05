package com.dicoding.mycatapplication.core.data.local

import com.dicoding.mycatapplication.core.data.local.database.BreedDao
import com.dicoding.mycatapplication.core.domain.BreedEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class LocalDataSource @Inject constructor(private val dao: BreedDao) {

    fun getListOfBreedsWithFlow(): Flow<List<BreedEntity>> = dao.getBreedsFromDBWithFlow()
    fun getBreedById(id: Int): Flow<BreedEntity> = dao.getBreedByID(id)
    fun getListOfFavoritesBreed(): Flow<List<BreedEntity>> = dao.getFavBreeds()
    suspend fun deleteBreed(breed: BreedEntity) = dao.deleteBreed(breed)
    suspend fun insertAllBreed(breeds: List<BreedEntity>) = dao.insertListBreeds(breeds)
    suspend fun deleteAll() = dao.deleteAll()
    suspend fun saveFavoriteBreed(breed: BreedEntity, favoriteState: Boolean) {
        breed.favorite = favoriteState
        dao.saveBreedToFavorite(breed)
    }

}