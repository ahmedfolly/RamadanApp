package com.da3wa.ramadanapp.features.media.data.mappers

import com.da3wa.ramadanapp.common.domain.mapper.IMapper
import com.da3wa.ramadanapp.features.media.data.model.dtos.PlaylistDto
import com.da3wa.ramadanapp.features.media.data.model.dtos.VideoDto
import com.da3wa.ramadanapp.features.media.domain.model.Playlist
import com.da3wa.ramadanapp.features.media.domain.model.Video

object MediaMapper : IMapper<Playlist, PlaylistDto, Unit>() {
	override fun fromDtoToDomain(dto: PlaylistDto): Playlist {
		return Playlist(
			videos = dto.videos?.map { videoDto -> videoDto.toVideo() }.orEmpty()
		)
	}
	private fun VideoDto.toVideo(): Video {
		return Video(
			category = category.orEmpty(),
			subCategory = subCategory.orEmpty(),
			title = title.orEmpty(),
			url = url.orEmpty(),
			videoId = extractVideoId(url.orEmpty()).orEmpty()
		)
	}


	private fun extractVideoId(url: String): String? {
		val regex = ".*(?:youtu.be/|v/|watch\\?v=|embed/|shorts/|\\?v=)([^#&?]*).*".toRegex()
		val matchResult = regex.find(url)
		return matchResult?.groups?.get(1)?.value
	}
}