package com.dicoding.mycatapplication.core.domain

import androidx.lifecycle.LiveData
import com.dicoding.mycatapplication.core.data.local.database.BreedEntity
import com.dicoding.mycatapplication.core.util.Result
import kotlinx.coroutines.flow.Flow

interface BreedUseCase {

    fun getListBreeds(): Flow<Result<List<BreedDomain>>>
    fun getBreedById(id: Int): LiveData<BreedDomain>
    fun getFavBreed(): Flow<Result<List<BreedDomain>>>

    suspend fun deleteBreed(item: BreedDomain)
    suspend fun updateBreed(item: BreedDomain, state: Boolean)
}