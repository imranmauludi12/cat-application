package com.dicoding.mycatapplication.core.domain

import androidx.lifecycle.LiveData
import com.dicoding.mycatapplication.core.di.Result
import kotlinx.coroutines.flow.Flow

interface IBreedRepository {

    fun getListOfBreeds(): Flow<Result<List<BreedEntity>>>
    fun getBreedByBreedId(id: Int): LiveData<BreedEntity>
    fun getFavoriteBreed(): Flow<Result<List<BreedEntity>>>

    suspend fun deleteBreed(item: BreedEntity)
    suspend fun updateBreed(breedEntity: BreedEntity, state: Boolean)
}