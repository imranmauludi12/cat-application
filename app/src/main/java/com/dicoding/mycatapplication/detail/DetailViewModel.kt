package com.dicoding.mycatapplication.detail

import androidx.lifecycle.*
import com.dicoding.mycatapplication.core.data.local.database.BreedEntity
import com.dicoding.mycatapplication.core.domain.BreedDomain
import com.dicoding.mycatapplication.core.domain.BreedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val breedUseCase: BreedUseCase): ViewModel() {

    private val _breedID = MutableLiveData<Int>()

    val breedDetail: LiveData<BreedDomain> = _breedID.switchMap {
        breedUseCase.getBreedById(it)
    }

    fun getBreedById(id: Int) {
        _breedID.value = id
    }

    fun saveBreedToFavorite(item: BreedDomain, state: Boolean) = viewModelScope.launch {
        breedUseCase.updateBreed(item, state)
    }

    fun deleteBreed(item: BreedDomain) = viewModelScope.launch {
        breedUseCase.deleteBreed(item)
    }

}