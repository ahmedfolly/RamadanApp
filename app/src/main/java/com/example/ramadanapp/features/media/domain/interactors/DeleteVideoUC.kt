package com.example.ramadanapp.features.media.domain.interactors

import com.example.ramadanapp.common.domain.models.RamadanAppException
import com.example.ramadanapp.common.domain.models.Resource
import com.example.ramadanapp.features.media.domain.model.Video
import com.example.ramadanapp.features.media.domain.repos.IMediaRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class DeleteVideoUC(private val mediaRepo: IMediaRepo) {
	 operator fun invoke(video: Video) = flow {
		emit(Resource.Loading)
		mediaRepo.deleteVideo(video)
		emit(Resource.Success(video))
	}.catch { e ->
		val exception =
			if (e is RamadanAppException) e else RamadanAppException.UnknownException("Unknown error appeared")
		emit(Resource.Failure(exception))
	}
}