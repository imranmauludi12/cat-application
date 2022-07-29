package com.dicoding.mycatapplication.core.di

import com.dicoding.mycatapplication.core.domain.BreedUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {

    fun bindMyUseCase(): BreedUseCase
}