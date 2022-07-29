package com.dicoding.mycatapplication.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mycatapplication.core.util.Result
import com.dicoding.mycatapplication.core.domain.BreedDomain
import com.dicoding.mycatapplication.core.domain.BreedUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavoriteViewModel constructor(
    private val breedUseCase: BreedUseCase
): ViewModel() {

    val favoriteListOfBreed: Flow<Result<List<BreedDomain>>> = breedUseCase.getFavBreed()

    fun updateFavorite(item: BreedDomain, state: Boolean) = viewModelScope.launch {
        breedUseCase.updateBreed(item, state)
    }
}