package com.example.ramadanapp.features.media.presentation.saved.mvi

import com.example.ramadanapp.common.domain.models.RamadanAppException
import com.example.ramadanapp.features.media.domain.model.Video

sealed class SavedVideosScreenState {
	data object Idle: SavedVideosScreenState()
	data object Loading : SavedVideosScreenState()
	data class Success(val videos: List<Video>) : SavedVideosScreenState()
	data class Failure(val error: RamadanAppException) : SavedVideosScreenState()
}