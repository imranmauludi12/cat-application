package com.dicoding.mycatapplication.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dicoding.mycatapplication.core.data.local.database.BreedDao
import com.dicoding.mycatapplication.core.data.local.LocalDataSource
import com.dicoding.mycatapplication.core.data.remote.network.ApiService
import com.dicoding.mycatapplication.core.data.remote.RemoteDataSource
import com.dicoding.mycatapplication.core.domain.BreedEntity
import com.dicoding.mycatapplication.core.domain.IBreedRepository
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BreedRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: ApiService
    private lateinit var breedDao: BreedDao
    private lateinit var breedRepository: IBreedRepository

    @Before
    fun setUp() {
        val localDataSource = LocalDataSource.getInstance(breedDao)
        val remoteDataSource = RemoteDataSource.getInstance(apiService)
        breedRepository = BreedRepository.getInstance(localDataSource, remoteDataSource)
    }

    @Test
    fun `get list of breeds and success`() {
        val expectedListOfBreeds = ArrayList<BreedEntity>()
        expectedListOfBreeds.add(
            BreedEntity(
                breedName = "akasia",
                origin = "bogor",
                country = "indonesia",
                pattern = "corak"
            )
        )

        `when`(breedRepository.getListOfBreeds()).thenReturn(expectedListOfBreeds)
        val actualOutput = breedRepository.getListOfBreeds()
        Mockito.verify(breedRepository.getListOfBreeds())
        assertNotNull(actualOutput)
    }
}