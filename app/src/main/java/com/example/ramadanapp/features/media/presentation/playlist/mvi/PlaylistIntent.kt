package com.example.ramadanapp.features.media.presentation.playlist.mvi

import com.example.ramadanapp.features.media.domain.model.Playlist
import com.example.ramadanapp.features.media.domain.model.Video

sealed class PlaylistIntent {
	data class SaveVideo(val video: Video) : PlaylistIntent()
	data class GetSavedVideoById(val videoId: String) : PlaylistIntent()
	data class DeleteVideo(val video:Video): PlaylistIntent()
//	data object GetSavedVideos : PlaylistIntent()
}