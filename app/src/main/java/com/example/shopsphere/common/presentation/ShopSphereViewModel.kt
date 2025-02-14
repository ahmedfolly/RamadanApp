package com.example.shopsphere.common.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class ShopSphereViewModel<Intent,UiState>(initialState:UiState): ViewModel() {
	val userIntentChannel = Channel<Intent>(Channel.UNLIMITED)
	private val _state: MutableStateFlow<UiState> = MutableStateFlow(initialState)
	val state = _state.asStateFlow()

	init {
		processIntent()
	}
	abstract fun processIntent()
	fun setState(newState:UiState){
		_state.update { newState }
	}
	abstract fun clearState()
}