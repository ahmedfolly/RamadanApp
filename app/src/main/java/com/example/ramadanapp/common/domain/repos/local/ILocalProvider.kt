package com.example.ramadanapp.common.domain.repos.local

interface ILocalProvider {
	suspend fun <T> save(data: T)
	suspend fun <T> get(): T
	suspend fun <T> update(data: T)
	suspend fun <T> delete(data: T)
}