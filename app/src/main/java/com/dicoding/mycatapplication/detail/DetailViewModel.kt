package com.dicoding.mycatapplication.detail

import androidx.lifecycle.*
import com.dicoding.mycatapplication.core.domain.BreedEntity
import com.dicoding.mycatapplication.core.domain.BreedUseCase
import kotlinx.coroutines.launch

class DetailViewModel(private val breedUseCase: BreedUseCase): ViewModel() {

    private val _breedID = MutableLiveData<Int>()

    val breedDetail: LiveData<BreedEntity> = _breedID.switchMap {
        breedUseCase.getBreedById(it)
    }

    fun getBreedById(id: Int) {
        _breedID.value = id
    }

    fun saveBreedToFavorite(item: BreedEntity, state: Boolean) = viewModelScope.launch {
        breedUseCase.updateBreed(item, state)
    }

}