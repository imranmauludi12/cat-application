package com.dicoding.mycatapplication.core.util

sealed class Resource<out R> private constructor() {
    data class Success<out T>(val data: T): Resource<T>()
    data class Empty(val cause: String): Resource<Nothing>()
}