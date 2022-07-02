package com.dicoding.mycatapplication.core.data.remote

import android.util.Log
import com.dicoding.mycatapplication.core.data.remote.network.ApiService
import com.dicoding.mycatapplication.core.data.remote.response.DataItem
import com.dicoding.mycatapplication.core.di.Resource
import java.lang.Exception

class RemoteDataSource private constructor(
    private val apiService: ApiService
) {

    suspend fun getBreedFacts(): Resource<List<DataItem>> {
        return try {
            var data: Resource<List<DataItem>> = Resource.Empty("not yet")
            val networkRequest = apiService.getListOfBreeds(PAGE_SIZE)
            val responseData = networkRequest.data
            if (responseData != null && responseData.isNotEmpty()) {
                data = Resource.Success(responseData)
            }
            data
        } catch (e: Exception) {
            Log.d(TAG, "cause: ${e.message.toString()}")
            Resource.Empty("Failed to get data from api")
        }

    }

    companion object {
        private var INSTANCE: RemoteDataSource? = null

        fun getInstance(apiService: ApiService): RemoteDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RemoteDataSource(apiService)
            }

        private const val PAGE_SIZE = 1
        private const val TAG = "cek remoteDataSource"
    }

}