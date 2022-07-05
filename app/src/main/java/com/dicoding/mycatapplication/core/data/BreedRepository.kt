package com.dicoding.mycatapplication.core.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.dicoding.mycatapplication.core.data.local.LocalDataSource
import com.dicoding.mycatapplication.core.data.remote.RemoteDataSource
import com.dicoding.mycatapplication.core.util.Resource
import com.dicoding.mycatapplication.core.util.Result
import com.dicoding.mycatapplication.core.domain.BreedEntity
import com.dicoding.mycatapplication.core.domain.IBreedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BreedRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
): IBreedRepository {

    override fun getListOfBreeds(): Flow<Result<List<BreedEntity>>> = flow {
        val existingData = localDataSource.getListOfBreedsWithFlow()
        if (existingData.first().isEmpty()) {
            emit(Result.Loading)
            when (val data = remoteDataSource.getBreedFacts()) {
                is Resource.Success -> {
                    val listOfBreed = data.data.map {
                        BreedEntity(
                            breedName = it.breed,
                            origin = it.origin,
                            country = it.country,
                            pattern = it.pattern
                        )
                    }
                    localDataSource.deleteAll()
                    localDataSource.insertAllBreed(listOfBreed)
                }
                is Resource.Empty -> {
                    emit(Result.Error("Failed API request!"))
                }
            }
            val localData: Flow<Result<List<BreedEntity>>> = localDataSource.getListOfBreedsWithFlow().map { Result.Success(it) }
            emitAll(localData)
        } else {
            val localData: Flow<Result<List<BreedEntity>>> = localDataSource.getListOfBreedsWithFlow().map { Result.Success(it) }
            emitAll(localData)
        }

    }.catch { exception ->
        Log.d(TAG, "cause: ${exception.message.toString()}")
        emit(Result.Error("Failed to get data"))
    }.flowOn(Dispatchers.IO)

    override fun getBreedByBreedId(id: Int): LiveData<BreedEntity> =
        localDataSource.getBreedById(id).asLiveData()

    override fun getFavoriteBreed(): Flow<Result<List<BreedEntity>>> =
        localDataSource.getListOfFavoritesBreed()
            .map { Result.Success(it) }

    override suspend fun deleteBreed(item: BreedEntity) {
        localDataSource.deleteBreed(item)
    }

    override suspend fun updateBreed(breedEntity: BreedEntity, state: Boolean) {
        localDataSource.saveFavoriteBreed(breedEntity, state)
    }

    companion object {
        private const val TAG = "cek breedRepository"
    }

}