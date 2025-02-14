package com.example.shopsphere.features.products.data.repos

import com.example.shopsphere.features.products.data.mappers.ProductsMapper.convertEntitiesToDomains
import com.example.shopsphere.features.products.data.mappers.ProductsMapper.convertDtoToDomains
import com.example.shopsphere.features.products.data.mappers.ProductsMapper.convertToEntities
import com.example.shopsphere.features.products.domain.models.Product
import com.example.shopsphere.features.products.domain.repos.IProductsRepo
import com.example.shopsphere.features.products.domain.repos.local.ILocalProducts
import com.example.shopsphere.features.products.domain.repos.remote.IProductsRemote

class ProductsRepo(
	private val productsRemote: IProductsRemote,
	private val localProductsRepo: ILocalProducts
) : IProductsRepo {
	override suspend fun getRemoteProducts(): List<Product> {
		return productsRemote.getAllProducts().products?.convertDtoToDomains().orEmpty()
	}

	override suspend fun getLocalProducts(): List<Product> {
		return localProductsRepo.getProducts().convertEntitiesToDomains()
	}

	override suspend fun saveProducts(products: List<Product>) {
		localProductsRepo.saveProducts(products.convertToEntities())
	}

	override suspend fun searchForProducts(productName: String): List<Product> {
		return productsRemote.searchForProduct(productName).products?.convertDtoToDomains()
			.orEmpty()
	}

	override suspend fun getCategoryProducts(categoryName: String): List<Product> {
		return productsRemote.getCategoryProducts(categoryName).products?.convertDtoToDomains()
			.orEmpty()
	}
}