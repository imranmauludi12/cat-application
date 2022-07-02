package com.dicoding.mycatapplication.core.di

import android.content.Context
import com.dicoding.mycatapplication.core.data.BreedRepository
import com.dicoding.mycatapplication.core.data.local.database.AppDatabase
import com.dicoding.mycatapplication.core.data.local.LocalDataSource
import com.dicoding.mycatapplication.core.data.remote.network.ApiConfig
import com.dicoding.mycatapplication.core.data.remote.RemoteDataSource
import com.dicoding.mycatapplication.core.domain.BreedInteractor
import com.dicoding.mycatapplication.core.domain.BreedUseCase

object Injection {

    fun provideBreedUseCase(context: Context): BreedUseCase {
        val repository = provideBreedRepository(context)
        return BreedInteractor(repository)
    }

    private fun provideBreedRepository(context: Context): BreedRepository {
        val local = provideLocalDataSource(context)
        val remote = provideRemoteDataSource()
        return BreedRepository.getInstance(local, remote)
    }

    private fun provideLocalDataSource(context: Context): LocalDataSource {
        val database = AppDatabase.getInstance(context)
        val breedDao = database.breedDao()
        return LocalDataSource.getInstance(breedDao)
    }

    private fun provideRemoteDataSource(): RemoteDataSource {
        val apiService = ApiConfig().getApiService()
        return RemoteDataSource.getInstance(apiService)
    }

}