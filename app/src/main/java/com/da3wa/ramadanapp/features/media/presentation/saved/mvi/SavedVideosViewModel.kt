package com.da3wa.ramadanapp.features.media.presentation.saved.mvi

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.da3wa.ramadanapp.common.domain.models.Resource
import com.da3wa.ramadanapp.common.presentation.RamadanAppViewModel
import com.da3wa.ramadanapp.features.media.domain.interactors.GetSavedVideosUC
import com.da3wa.ramadanapp.features.media.domain.interactors.SaveLastSeenUC
import com.da3wa.ramadanapp.features.media.domain.model.Video
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedVideosViewModel @Inject constructor(private val getSavedVideosUC: GetSavedVideosUC,private val saveLastSeenUC: SaveLastSeenUC) :
	RamadanAppViewModel<SavedScreenIntents, SavedVideosScreenState>(
		SavedVideosScreenState.Idle
	) {
	override fun processIntent() {
		viewModelScope.launch {
			userIntentChannel.consumeAsFlow().collect { intent ->
				when (intent) {
					is SavedScreenIntents.DeleteFromSavedVideos -> {}
					is SavedScreenIntents.LoadSavedVideos          -> loadSavedVideos()
					is SavedScreenIntents.SaveLastSeenVideo     -> saveLastSeenVideo(intent.video)
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

	private fun saveLastSeenVideo(video: Video){
		viewModelScope.launch{
			saveLastSeenUC(video).collect{response->
				if (response is Resource.Success){
					Log.d("TAG", "saveLastSeenVideo: ${response.model}")
				}
			}
		}
	}
	override fun clearState() {
		TODO("Not yet implemented")
	}
}