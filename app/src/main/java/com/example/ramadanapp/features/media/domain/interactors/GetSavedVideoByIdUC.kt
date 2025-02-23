package com.example.ramadanapp.features.media.domain.interactors

import com.example.ramadanapp.common.domain.interactors.BaseUC
import com.example.ramadanapp.common.domain.models.RamadanAppException
import com.example.ramadanapp.features.media.domain.model.Video
import com.example.ramadanapp.features.media.domain.repos.IMediaRepo

class GetSavedVideoByIdUC(private val mediaRepo: IMediaRepo) : BaseUC<String, Video>() {
	override suspend fun execute(param: String?): Video {
		return if (param != null) mediaRepo.getSavedVideoById(param) else throw RamadanAppException.StorageException.LocalStorageException(
			"There is no video"
		)
	}
}