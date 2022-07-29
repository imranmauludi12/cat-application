package com.dicoding.mycatapplication.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.mycatapplication.core.domain.BreedUseCase
import javax.inject.Inject

class VIewModelFactory @Inject constructor(private val breedUseCase: BreedUseCase): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            FavoriteViewModel(breedUseCase) as T
        } else {
            throw Throwable("unknown model class: ${modelClass.name}")
        }
    }
}