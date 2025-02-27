package com.da3wa.ramadanapp.features.sections.presentation.mvi

sealed class MainIntents {
	data object LoadSections : MainIntents()
	data object GetLastSeenVideo: MainIntents()

}