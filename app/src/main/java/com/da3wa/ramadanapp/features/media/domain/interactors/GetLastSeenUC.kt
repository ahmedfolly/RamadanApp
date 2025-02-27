package com.da3wa.ramadanapp.features.media.domain.interactors

import com.da3wa.ramadanapp.common.domain.interactors.BaseUC
import com.da3wa.ramadanapp.features.media.domain.model.Video
import com.da3wa.ramadanapp.features.media.domain.repos.IMediaRepo

class GetLastSeenUC(private val mediaRepo: IMediaRepo) : BaseUC<Unit,Video>(){
	override suspend fun execute(param: Unit?): Video {
		return mediaRepo.getLastSeenVideo()
	}
}