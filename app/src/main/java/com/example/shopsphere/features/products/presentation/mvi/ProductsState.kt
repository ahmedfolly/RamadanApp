package com.example.shopsphere.features.products.presentation.mvi

import com.example.shopsphere.common.domain.models.ShopSphereException
import com.example.shopsphere.features.products.domain.models.Product

 sealed class ProductsState {
	data object Loading : ProductsState()
	data class Success(val products: List<Product>) : ProductsState()
	data class Failure(val e: ShopSphereException) : ProductsState()
}