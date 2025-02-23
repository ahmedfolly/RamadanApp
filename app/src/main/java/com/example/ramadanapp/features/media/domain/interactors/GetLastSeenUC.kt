package com.example.ramadanapp.features.media.domain.interactors

import com.example.ramadanapp.common.domain.models.RamadanAppException
import com.example.ramadanapp.common.domain.models.Resource
import com.example.ramadanapp.features.media.domain.repos.IMediaRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetLastSeenUC(private val mediaRepo: IMediaRepo) {
	operator fun invoke() = flow {
		emit(Resource.Loading)
		val lastSeenVideo = mediaRepo.getLastSeenVideo()
		emit(Resource.Success(lastSeenVideo))
	}.catch { e ->
		val exception = if (e is RamadanAppException) e
		else RamadanAppException.UnknownException("Unknown error occurred ${e.message}")
		emit(Resource.Failure(exception))
	}.flowOn(Dispatchers.IO)
}