package com.example.ramadanapp.features.media.presentation.saved.mvi

import com.example.ramadanapp.features.media.domain.model.Video

sealed class SavedScreenIntents {
	data object LoadSavedVideos : SavedScreenIntents()
	data class DeleteFromSavedVideos(val video: Video) : SavedScreenIntents()
}