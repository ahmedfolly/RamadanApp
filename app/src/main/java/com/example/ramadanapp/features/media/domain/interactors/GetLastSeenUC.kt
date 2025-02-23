package com.example.ramadanapp.features.media.domain.interactors

import com.example.ramadanapp.common.domain.interactors.BaseUC
import com.example.ramadanapp.features.media.domain.model.Video
import com.example.ramadanapp.features.media.domain.repos.IMediaRepo

class GetLastSeenUC(private val mediaRepo: IMediaRepo) : BaseUC<Unit,Video>(){
	override suspend fun execute(param: Unit?): Video {
		return mediaRepo.getLastSeenVideo()
	}
}