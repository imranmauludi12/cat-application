package com.dicoding.mycatapplication.core.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.dicoding.mycatapplication.core.data.local.database.BreedEntity
import com.dicoding.mycatapplication.core.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BreedInteractor @Inject constructor(private val breedRepository: IBreedRepository):
    BreedUseCase {

    override fun getListBreeds(): Flow<Result<List<BreedDomain>>> {
        return breedRepository.getListOfBreeds()
    }

    override fun getBreedById(id: Int): LiveData<BreedDomain> {
        return breedRepository.getBreedByBreedId(id)
    }

    override fun getFavBreed(): Flow<Result<List<BreedDomain>>> {
        return breedRepository.getFavoriteBreed()
    }

    override suspend fun deleteBreed(item: BreedDomain) {
        val entity = item.changeIntoEntityModel()
        breedRepository.deleteBreed(entity)
    }

    override suspend fun updateBreed(item: BreedDomain, state: Boolean) {
        val entity = item.changeIntoEntityModel()
        breedRepository.updateBreed(entity, state)
    }

}