package com.example.ramadanapp.features.media.presentation.saved.mvi

import androidx.lifecycle.viewModelScope
import com.example.ramadanapp.common.domain.models.Resource
import com.example.ramadanapp.common.presentation.RamadanAppViewModel
import com.example.ramadanapp.features.media.domain.interactors.GetSavedVideosUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedVideosViewModel @Inject constructor(private val getSavedVideosUC: GetSavedVideosUC) :
	RamadanAppViewModel<SavedScreenIntents, SavedVideosScreenState>(
		SavedVideosScreenState.Idle
	) {
	override fun processIntent() {
		viewModelScope.launch {
			userIntentChannel.consumeAsFlow().collect { intent ->
				when (intent) {
					is SavedScreenIntents.DeleteFromSavedVideos -> {}
					SavedScreenIntents.LoadSavedVideos          -> loadSavedVideos()
				}
			}
		}
	}

	private fun loadSavedVideos() {
		viewModelScope.launch {
			val savedVideosResponse = getSavedVideosUC()
			savedVideosResponse.collect { response ->
				when (response) {
					is Resource.Failure -> setState(SavedVideosScreenState.Failure(response.exception))
					Resource.Loading    -> setState(SavedVideosScreenState.Loading)
					is Resource.Success -> setState(SavedVideosScreenState.Success(response.model))
				}
			}
		}
	}

	override fun clearState() {
		TODO("Not yet implemented")
	}
}