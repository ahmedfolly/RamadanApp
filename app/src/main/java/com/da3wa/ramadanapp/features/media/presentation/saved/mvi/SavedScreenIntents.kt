package com.da3wa.ramadanapp.features.media.presentation.saved.mvi

import com.da3wa.ramadanapp.features.media.domain.model.Video

sealed class SavedScreenIntents {
	data object LoadSavedVideos : SavedScreenIntents()
	data class DeleteFromSavedVideos(val video: Video) : SavedScreenIntents()
	data class SaveLastSeenVideo(val video: Video): SavedScreenIntents()
}