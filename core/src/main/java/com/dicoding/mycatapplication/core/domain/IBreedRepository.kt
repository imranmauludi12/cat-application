package com.dicoding.mycatapplication.core.domain

import androidx.lifecycle.LiveData
import com.dicoding.mycatapplication.core.data.local.database.BreedEntity
import com.dicoding.mycatapplication.core.util.Result
import kotlinx.coroutines.flow.Flow

interface IBreedRepository {

    fun getListOfBreeds(): Flow<Result<List<BreedDomain>>>
    fun getBreedByBreedId(id: Int): LiveData<BreedDomain>
    fun getFavoriteBreed(): Flow<Result<List<BreedDomain>>>

    suspend fun deleteBreed(item: BreedEntity)
    suspend fun updateBreed(breedEntity: BreedEntity, state: Boolean)
}