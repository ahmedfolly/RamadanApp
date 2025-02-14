package com.example.shopsphere.features.categories.presentation.mvi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopsphere.common.domain.models.Resource
import com.example.shopsphere.common.presentation.ShopSphereViewModel
import com.example.shopsphere.features.categories.domain.interactors.GetCategoriesUC
import com.example.shopsphere.features.categories.domain.models.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(private val getCategoriesUC: GetCategoriesUC) :
	ShopSphereViewModel<CategoriesIntents, CategoriesState>(
		CategoriesState.Loading
	) {

	override fun processIntent() {
		viewModelScope.launch {
			userIntentChannel.consumeAsFlow().collect { intent ->
				when (intent) {
					CategoriesIntents.GetCategories -> {
						getCategories()
						Log.d("TAG","processIntent$intent")
					}

				}
			}
		}
	}

	override fun clearState() {
		setState(CategoriesState.Idle)
	}

	private fun getCategories() {
		viewModelScope.launch {
			val categoriesResponse = getCategoriesUC()
			categoriesResponse.collect { response ->
				when (response) {
					is Resource.Failure -> setState(
						CategoriesState.Failure(
							response.exception
						)
					)
					is Resource.Loading    -> setState(CategoriesState.Loading)
					is Resource.Success -> {
						setState(CategoriesState.Success(response.model))
					}

				}
			}
		}
	}
}