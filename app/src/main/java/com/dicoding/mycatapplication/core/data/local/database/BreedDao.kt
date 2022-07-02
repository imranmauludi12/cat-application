package com.dicoding.mycatapplication.core.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.mycatapplication.core.domain.BreedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedDao {

    @Query("SELECT * FROM breeds ORDER BY breed_name ASC")
    fun getBreedsFromDBWithFlow(): Flow<List<BreedEntity>>

    @Query("SELECT * FROM breeds WHERE favorite = 1")
    fun getFavBreeds(): Flow<List<BreedEntity>>

    @Query("SELECT * FROM breeds WHERE id = :id")
    fun getBreedByID(id: Int): Flow<BreedEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBreed(vararg breed: BreedEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListBreeds(breeds: List<BreedEntity>)

    @Update
    suspend fun saveBreedToFavorite(breed: BreedEntity)

    @Delete
    suspend fun deleteBreed(breed: BreedEntity)

    @Query("DELETE FROM breeds")
    suspend fun deleteAll()

}