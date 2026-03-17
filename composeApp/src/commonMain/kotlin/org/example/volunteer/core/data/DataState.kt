package org.example.volunteer.core.data

sealed class DataState<T>{
    data class Success<T>(val data:T): DataState<T>()
    data class Error(val message:String): DataState<Nothing>()
    object Loading: DataState<Nothing>()
}