package com.dicoding.mycatapplication.core.data.local

import androidx.lifecycle.LiveData
import com.dicoding.mycatapplication.core.data.local.database.BreedDao
import com.dicoding.mycatapplication.core.domain.BreedEntity
import kotlinx.coroutines.flow.Flow

open class LocalDataSource private constructor(private val dao: BreedDao) {

    companion object {
        @Volatile
        var INSTANCE: LocalDataSource? = null

        fun getInstance(dao: BreedDao): LocalDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocalDataSource(dao)
            }

    }

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