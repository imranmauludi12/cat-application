package com.dicoding.mycatapplication.core.di

import android.content.Context
import com.dicoding.mycatapplication.core.data.BreedRepository
import com.dicoding.mycatapplication.core.data.local.LocalDataSource
import com.dicoding.mycatapplication.core.data.local.database.AppDatabase
import com.dicoding.mycatapplication.core.data.remote.RemoteDataSource
import com.dicoding.mycatapplication.core.data.remote.network.ApiConfig
import com.dicoding.mycatapplication.core.domain.BreedInteractor
import com.dicoding.mycatapplication.core.domain.BreedUseCase
import com.dicoding.mycatapplication.core.domain.IBreedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelListModule {

    @Provides
    @Singleton
    fun provideUseCase(repository: IBreedRepository) : BreedUseCase {
        return BreedInteractor(repository)
    }

    @Provides
    fun provideRepository(
        remote: RemoteDataSource,
        local: LocalDataSource
    ) : IBreedRepository {
        return BreedRepository(remoteDataSource = remote, localDataSource = local)
    }

    @Provides
    fun provideLocalDataSource(@ApplicationContext context: Context) : LocalDataSource {
        val database = AppDatabase.getInstance(context)
        return LocalDataSource(database.breedDao())
    }

    @Provides
    fun provideRemoteDataSource(): RemoteDataSource {
        val apiService = ApiConfig().getApiService()
        return RemoteDataSource(apiService)
    }
}