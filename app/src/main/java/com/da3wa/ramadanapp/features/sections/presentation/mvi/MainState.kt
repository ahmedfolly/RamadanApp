package com.da3wa.ramadanapp.features.sections.presentation.mvi

import com.da3wa.ramadanapp.common.domain.models.RamadanAppException
import com.da3wa.ramadanapp.features.sections.domain.models.Section

sealed class SectionsState {
	data object Idle: SectionsState()
	data object Loading: SectionsState()
	data class Success(val sections: List<Section>): SectionsState()
	data class Failure(val e: RamadanAppException): SectionsState()
}