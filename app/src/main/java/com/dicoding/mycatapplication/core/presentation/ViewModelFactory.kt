package com.dicoding.mycatapplication.core.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.mycatapplication.core.di.Injection
import com.dicoding.mycatapplication.core.domain.BreedUseCase
import com.dicoding.mycatapplication.detail.DetailViewModel
import com.dicoding.mycatapplication.favorite.FavoriteViewModel
import com.dicoding.mycatapplication.list.ListViewModel

class ViewModelFactory private constructor(
    private val breedUseCase: BreedUseCase
    ) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ListViewModel::class.java) -> {
                ListViewModel(breedUseCase) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(breedUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(breedUseCase) as T
            }
            else -> throw Throwable("unknown view model class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideBreedUseCase(context))
            }
        }
    }

}