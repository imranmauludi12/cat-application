package com.dicoding.mycatapplication.core.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.dicoding.mycatapplication.core.di.Result
import kotlinx.coroutines.flow.Flow

class BreedInteractor(private val breedRepository: IBreedRepository): BreedUseCase {
    override fun getListBreeds(): LiveData<Result<List<BreedEntity>>> {
        return breedRepository.getListOfBreeds().asLiveData()
    }

    override fun getBreedById(id: Int): LiveData<BreedEntity> {
        return breedRepository.getBreedByBreedId(id)
    }

    override fun getFavBreed(): Flow<Result<List<BreedEntity>>> {
//        return breedRepository.getFavoriteBreed().asLiveData()
        return breedRepository.getFavoriteBreed()
    }

    override suspend fun deleteBreed(item: BreedEntity) {
        breedRepository.deleteBreed(item)
    }

    override suspend fun updateBreed(item: BreedEntity, state: Boolean) {
        breedRepository.updateBreed(item, state)
    }

}