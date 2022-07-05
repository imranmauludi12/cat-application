package com.dicoding.mycatapplication.core.domain

import androidx.lifecycle.LiveData
import com.dicoding.mycatapplication.core.util.Result
import kotlinx.coroutines.flow.Flow

interface BreedUseCase {

    fun getListBreeds(): LiveData<Result<List<BreedEntity>>>
    fun getBreedById(id: Int): LiveData<BreedEntity>
    fun getFavBreed(): Flow<Result<List<BreedEntity>>>

    suspend fun deleteBreed(item: BreedEntity)
    suspend fun updateBreed(item: BreedEntity, state: Boolean)
}