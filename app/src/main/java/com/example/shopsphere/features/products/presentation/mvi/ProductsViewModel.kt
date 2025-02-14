package com.example.shopsphere.features.products.presentation.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopsphere.common.domain.models.Resource
import com.example.shopsphere.common.presentation.ShopSphereViewModel
import com.example.shopsphere.common.utils.handleResponse
import com.example.shopsphere.features.products.domain.interactors.GetAllProductsUC
import com.example.shopsphere.features.products.domain.interactors.GetCategoryProductsUC
import com.example.shopsphere.features.products.domain.interactors.SearchForProductUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
	private val getAllProductsUC: GetAllProductsUC,
	private val searchForProductsUC: SearchForProductUC,
	private val getCategoryProductsUC: GetCategoryProductsUC
) :
	ShopSphereViewModel<UserIntents, ProductsState>(
		ProductsState.Loading
	) {
	override fun processIntent() {
		viewModelScope.launch {
			userIntentChannel.consumeAsFlow().collect { intent ->
				when (intent) {
					is UserIntents.LoadProducts     -> getProducts()
					is UserIntents.SearchForProduct -> searchForProducts(intent.productName)
					is UserIntents.CategoryProducts -> getCategoryProducts(intent.categoryName)
				}
			}
		}
	}

	override fun clearState() {
		setState(ProductsState.Loading)
	}

	private fun getProducts() {
		viewModelScope.launch {
			val productsResponse = getAllProductsUC()
			productsResponse.collect { response ->
				when (response) {
					is Resource.Failure -> setState(ProductsState.Failure(response.exception))
					Resource.Loading    -> setState(ProductsState.Loading)
					is Resource.Success -> setState(ProductsState.Success(response.model))
				}
			}
		}
	}

	private fun searchForProducts(productName: String) {
		viewModelScope.launch {
			val searchResponse = searchForProductsUC(productName)
			searchResponse.collect { response ->
				handleResponse(response, onFailure = { exception ->
					setState(ProductsState.Failure(exception))
				}, onSuccess = { productsResponse ->
					setState(ProductsState.Success(productsResponse))
				}, onLoading = {
					setState(ProductsState.Loading)
				})
			}
		}
	}

	private fun getCategoryProducts(categoryName: String) {
		viewModelScope.launch {
			val productsResponse = getCategoryProductsUC(categoryName)
			productsResponse.collect { response ->
				when (response) {
					is Resource.Failure -> setState(ProductsState.Failure(response.exception))
					Resource.Loading    -> setState(ProductsState.Loading)
					is Resource.Success -> setState(ProductsState.Success(response.model))
				}
			}
		}
	}

}