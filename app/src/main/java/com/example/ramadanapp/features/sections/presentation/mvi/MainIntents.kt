package com.example.ramadanapp.features.sections.presentation.mvi

import com.example.ramadanapp.features.media.presentation.main.mvi.MediaIntent

sealed class MainIntents {
	data object LoadSections : MainIntents()
	data object GetLastSeenVideo: MainIntents()

}