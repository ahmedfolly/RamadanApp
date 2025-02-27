package com.da3wa.ramadanapp.features.media.domain.interactors

import com.da3wa.ramadanapp.common.domain.interactors.BaseUC
import com.da3wa.ramadanapp.features.media.domain.model.Video
import com.da3wa.ramadanapp.features.media.domain.repos.IMediaRepo

class SaveVideoUC(private val mediaRepo: IMediaRepo) : BaseUC<Video, Unit>() {

	override suspend fun execute(param: Video?) {
		param?.let {
			mediaRepo.saveVideo(param)
		}
	}
}