package com.dicoding.mycatapplication.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dicoding.mycatapplication.core.data.local.database.BreedDao
import com.dicoding.mycatapplication.core.data.local.LocalDataSource
import com.dicoding.mycatapplication.core.data.remote.network.ApiService
import com.dicoding.mycatapplication.core.data.remote.RemoteDataSource
import com.dicoding.mycatapplication.core.data.local.database.BreedEntity
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

}