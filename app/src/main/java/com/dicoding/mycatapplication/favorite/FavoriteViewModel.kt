package com.dicoding.mycatapplication.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mycatapplication.core.util.Result
import com.dicoding.mycatapplication.core.domain.BreedEntity
import com.dicoding.mycatapplication.core.domain.BreedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val breedUseCase: BreedUseCase): ViewModel() {

    val favoriteListOfBreed: Flow<Result<List<BreedEntity>>> = breedUseCase.getFavBreed()

    fun updateFavorite(item: BreedEntity, state: Boolean) = viewModelScope.launch {
        breedUseCase.updateBreed(item, state)
    }
}