package com.example.ramadanapp.features.media.domain.interactors

import com.example.ramadanapp.common.domain.interactors.BaseUC
import com.example.ramadanapp.features.media.domain.model.Video
import com.example.ramadanapp.features.media.domain.repos.IMediaRepo

class SaveVideoUC(private val mediaRepo: IMediaRepo) : BaseUC<Video, Unit>() {

	override suspend fun execute(param: Video?) {
		param?.let {
			mediaRepo.saveVideo(param)
		}
	}
}