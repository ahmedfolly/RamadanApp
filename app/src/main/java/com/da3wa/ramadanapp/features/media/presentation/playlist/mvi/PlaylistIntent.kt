package com.da3wa.ramadanapp.features.media.presentation.playlist.mvi

import com.da3wa.ramadanapp.features.media.domain.model.Video

sealed class PlaylistIntent {
	data class GetSavedVideoById(val videoId: String) : PlaylistIntent()
	data class SaveOrDeleteVideo(val video: Video): PlaylistIntent()
	data class SaveLastSeenVideo(val video: Video): PlaylistIntent()
	data class LoadSectionItems(val categoryName:String): PlaylistIntent()
//	data object GetSavedVides : PlaylistIntent()
}