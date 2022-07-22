package com.dicoding.mycatapplication.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mycatapplication.core.util.Result
import com.dicoding.mycatapplication.core.data.local.database.BreedEntity
import com.dicoding.mycatapplication.core.domain.BreedDomain
import com.dicoding.mycatapplication.core.domain.BreedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val breedUseCase: BreedUseCase): ViewModel() {

//    val listOfCatBreeds: LiveData<Result<List<BreedDomain>>> = breedUseCase.getListBreeds()
    val listOfCatBreeds: Flow<Result<List<BreedDomain>?>> = breedUseCase.getListBreeds()

    fun deleteBreed(item: BreedDomain) = viewModelScope.launch {
        breedUseCase.deleteBreed(item)
    }
}