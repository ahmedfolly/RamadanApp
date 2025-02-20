package com.example.ramadanapp.features.media.data.mappers

import com.example.ramadanapp.common.domain.mapper.IMapper
import com.example.ramadanapp.features.media.data.model.entities.VideoEntity
import com.example.ramadanapp.features.media.domain.model.Video

object VideoMapper : IMapper<Video, Unit, VideoEntity>() {
	override fun fromEntityToDomain(entity: VideoEntity): Video {
		return Video(
			category = entity.category,
			subCategory = entity.subCategory,
			title = entity.title,
			url = entity.url,
			videoId = entity.videoId
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