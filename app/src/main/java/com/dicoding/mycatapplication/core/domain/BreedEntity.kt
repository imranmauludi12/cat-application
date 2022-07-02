package com.dicoding.mycatapplication.core.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "breeds")
data class BreedEntity(
    @NotNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @NotNull
    @ColumnInfo(name = "breed_name")
    val breedName: String,

    @NotNull
    @ColumnInfo(name = "country")
    val country: String,

    @NotNull
    @ColumnInfo(name = "origin")
    val origin: String,

    @NotNull
    @ColumnInfo(name = "pattern")
    val pattern: String,

    @NotNull
    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false
)