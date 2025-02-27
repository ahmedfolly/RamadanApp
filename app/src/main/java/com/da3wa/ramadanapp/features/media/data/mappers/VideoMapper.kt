package com.da3wa.ramadanapp.features.media.data.mappers

import com.da3wa.ramadanapp.common.domain.mapper.IMapper
import com.da3wa.ramadanapp.features.media.data.model.entities.VideoEntity
import com.da3wa.ramadanapp.features.media.domain.model.Video

object VideoMapper : IMapper<Video, Unit, VideoEntity>() {
	override fun fromEntityToDomain(entity: VideoEntity?): Video {
		return Video(
			category = entity?.category.orEmpty(),
			subCategory = entity?.subCategory.orEmpty(),
			title = entity?.title.orEmpty(),
			url = entity?.url.orEmpty(),
			videoId = entity?.videoId.orEmpty()
		)
	}

	override fun fromDomainToEntity(domain: Video): VideoEntity {
		return VideoEntity(
			videoId = domain.videoId,
			category = domain.category,
			subCategory = domain.subCategory,
			title = domain.title,
			url = domain.url
		)
	}
}