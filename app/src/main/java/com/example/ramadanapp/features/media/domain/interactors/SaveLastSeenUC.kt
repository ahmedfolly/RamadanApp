package com.example.ramadanapp.features.media.domain.interactors

import android.util.Log
import com.example.ramadanapp.common.domain.models.RamadanAppException
import com.example.ramadanapp.common.domain.models.Resource
import com.example.ramadanapp.features.media.domain.model.Video
import com.example.ramadanapp.features.media.domain.repos.IMediaRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SaveLastSeenUC(private val mediaRepo: IMediaRepo) {
	operator fun invoke(video: Video) = flow {
		emit(Resource.Loading)
		mediaRepo.saveLastSeenVideo(video)
		Log.d("TAG", "invoke save last seen: ")
		emit(Resource.Success(video))
	}.catch { e ->
		val exception = if (e is RamadanAppException) e
		else RamadanAppException.UnknownException("Unknown error occurred ${e.message}")
		Log.d("TAG", "invoke save last seen: ${exception.message}")
		emit(Resource.Failure(exception))
	}.flowOn(Dispatchers.IO)
}