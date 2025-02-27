package com.example.ramadanapp.features.sections.presentation.mvi

import androidx.lifecycle.viewModelScope
import com.example.ramadanapp.common.domain.models.Resource
import com.example.ramadanapp.common.presentation.RamadanAppViewModel
import com.example.ramadanapp.features.media.domain.interactors.GetLastSeenUC
import com.example.ramadanapp.features.media.domain.model.Video
import com.example.ramadanapp.features.sections.domain.interactors.GetMainSectionsUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
	private val getMainSectionsUC: GetMainSectionsUC,
	private val getLastSeenUC: GetLastSeenUC
) : RamadanAppViewModel<MainIntents, SectionsState>(
	SectionsState.Idle
) {
	private val _lastSeenVideoState: MutableStateFlow<Video?> = MutableStateFlow(null)
	val lastSeenVideoState = _lastSeenVideoState.asStateFlow()
	override fun processIntent() {
		viewModelScope.launch {
			userIntentChannel.consumeAsFlow().collect { userIntent ->
				when (userIntent) {
					MainIntents.GetLastSeenVideo -> getLastSeenVideo()
					MainIntents.LoadSections     -> loadSections()
				}
			}
		}
	}

	private fun loadSections() {
		viewModelScope.launch {
			getMainSectionsUC().collect { response ->
				when (response) {
					is Resource.Failure -> setState(SectionsState.Failure(response.exception))
					Resource.Loading    -> setState(SectionsState.Loading)
					is Resource.Success -> setState(SectionsState.Success(response.model))
				}
			}
		}
	}
//	private fun loadMedia() {
//		viewModelScope.launch {
//			val mediaResponse = getMediaUC()
//			mediaResponse.collect { response ->
//				when (response) {
//					is Resource.Failure -> setState(MediaState.Failure(response.exception))
//					is Resource.Loading -> setState(MediaState.Loading)
//					is Resource.Success -> setState(MediaState.Success(response.model))
//				}
//			}
//		}
//	}

	private fun getLastSeenVideo() {
		viewModelScope.launch {
			getLastSeenUC().collect { response ->
				if (response is Resource.Success) {
					_lastSeenVideoState.update {
						response.model
					}
				}
			}
		}
	}

	override fun clearState() {
		setState(SectionsState.Idle)
	}
}