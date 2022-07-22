package com.dicoding.mycatapplication.core.data.remote.network

import com.dicoding.mycatapplication.core.data.remote.response.GetBreedsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("breeds")
    suspend fun getListOfBreeds(
        @Query("page") page: Int
    ): GetBreedsResponse

}