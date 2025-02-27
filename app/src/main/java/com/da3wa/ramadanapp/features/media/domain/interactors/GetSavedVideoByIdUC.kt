package com.da3wa.ramadanapp.features.media.domain.interactors

import com.da3wa.ramadanapp.common.domain.interactors.BaseUC
import com.da3wa.ramadanapp.common.domain.models.RamadanAppException
import com.da3wa.ramadanapp.features.media.domain.model.Video
import com.da3wa.ramadanapp.features.media.domain.repos.IMediaRepo

class GetSavedVideoByIdUC(private val mediaRepo: IMediaRepo) : BaseUC<String, Video>() {
	override suspend fun execute(param: String?): Video {
		return if (param != null) mediaRepo.getSavedVideoById(param) else throw RamadanAppException.StorageException.LocalStorageException(
			"There is no video"
		)
	}
}