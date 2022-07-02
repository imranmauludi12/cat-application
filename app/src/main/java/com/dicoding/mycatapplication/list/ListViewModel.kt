package com.dicoding.mycatapplication.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mycatapplication.core.di.Result
import com.dicoding.mycatapplication.core.domain.BreedEntity
import com.dicoding.mycatapplication.core.domain.BreedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListViewModel(private val breedUseCase: BreedUseCase): ViewModel() {
    val listOfCatBreeds: LiveData<Result<List<BreedEntity>>> = breedUseCase.getListBreeds()

    fun deleteBreed(item: BreedEntity) = viewModelScope.launch {
        breedUseCase.deleteBreed(item)
    }
}