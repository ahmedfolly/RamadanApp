package com.da3wa.ramadanapp.features.media.domain.interactors

import com.da3wa.ramadanapp.common.domain.interactors.BaseUC
import com.da3wa.ramadanapp.features.media.domain.model.Video
import com.da3wa.ramadanapp.features.media.domain.repos.IMediaRepo

class GetSavedVideosUC(private val mediaRepo: IMediaRepo): BaseUC<Unit, List<Video>>() {
	override suspend fun execute(param: Unit?): List<Video> {
		return mediaRepo.getSavedVideos()
	}
}