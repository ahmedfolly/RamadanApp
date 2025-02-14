package com.example.shopsphere.features.products.domain.interactors

import com.example.shopsphere.common.domain.models.Resource
import com.example.shopsphere.common.domain.models.ShopSphereException
import com.example.shopsphere.features.products.domain.repos.IProductsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetAllProductsUC(private val productsRepo: IProductsRepo) {
	operator fun invoke() = flow {
		emit(Resource.Loading)
		val products = productsRepo.getRemoteProducts()
		if (!products.isEmpty())
			productsRepo.saveProducts(products)
		emit(Resource.Success(products))
	}.catch { e ->
		val localProducts = productsRepo.getLocalProducts()
		if (!localProducts.isEmpty())
			emit(Resource.Success(localProducts))
		else {
			val exception =
				if (e is ShopSphereException) e else ShopSphereException.UnknownException("Unknown error occurred ${e.message}")
			emit(Resource.Failure(exception))
		}
	}.flowOn(Dispatchers.IO)
}