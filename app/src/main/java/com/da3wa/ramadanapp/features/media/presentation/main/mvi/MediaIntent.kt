package com.da3wa.ramadanapp.features.media.presentation.main.mvi

sealed class MediaIntent {
	data object LoadMedia: MediaIntent()
	data object GetLastSeenVideo: MediaIntent()
}