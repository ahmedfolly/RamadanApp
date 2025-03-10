package com.example.ramadanapp.common.domain.models

sealed class Resource<out T> {
	data object Loading: Resource<Nothing>()
	data class Success<T>(val model:T): Resource<T>()
	data class Failure(val exception: RamadanAppException): Resource<Nothing>()
}