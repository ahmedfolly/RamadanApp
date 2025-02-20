package com.example.ramadanapp.features.media.domain.interactors

import com.example.ramadanapp.common.domain.models.RamadanAppException
import com.example.ramadanapp.common.domain.models.Resource
import com.example.ramadanapp.features.media.domain.model.Video
import com.example.ramadanapp.features.media.domain.repos.IMediaRepo

class SaveVideoUC(private val mediaRepo: IMediaRepo) {
	suspend operator fun invoke(video: Video): Resource<Video> {
		return try {
			Resource.Loading
			mediaRepo.saveVideo(video)
			Resource.Success(video)
		} catch (e: RamadanAppException) {
			Resource.Failure(e)
		}
	}
}