package com.example.ramadanapp.features.media.presentation.playlist.mvi

import com.example.ramadanapp.features.media.domain.model.Playlist
import com.example.ramadanapp.features.media.domain.model.Video

sealed class PlaylistIntent {
	data class GetSavedVideoById(val videoId: String) : PlaylistIntent()
	data class SaveOrDeleteVideo(val video: Video): PlaylistIntent()
	data class SaveLastSeenVideo(val video: Video): PlaylistIntent()
//	data object GetSavedVides : PlaylistIntent()
}