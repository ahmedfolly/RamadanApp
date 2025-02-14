package com.example.shopsphere.features.products.presentation.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load

import com.example.shopsphere.android.ShopSphereApp
import com.example.shopsphere.databinding.ProductItemBinding
import com.example.shopsphere.features.products.domain.models.Product

class ProductsAdapter : ListAdapter<Product, ProductsAdapter.ProductsVH>(ProductsDiffUtil()) {
	private lateinit var _binding: ProductItemBinding
	private val binding
		get()=_binding

	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): ProductsVH {
		_binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
		return ProductsVH(binding)
	}

	override fun onBindViewHolder(
		holder: ProductsVH,
		position: Int
	) {
		val product = getItem(position)
		holder.binds(product)
	}

	inner class ProductsVH(private val binding: ProductItemBinding): RecyclerView.ViewHolder(binding.root){
		fun binds(product: Product){
			val productImage = product.thumbnail
			binding.ivProductImage.load(productImage){
				crossfade(true)
			}
			val productTitle = product.title
			binding.tvProductName.text = productTitle
			val productDesc = product.description
			binding.tvProductDescription.text = productDesc
			val productRating = product.rating
			binding.tvProductRating.text = String.format(productRating.toString())
		}
	}
	private class ProductsDiffUtil: DiffUtil.ItemCallback<Product>(){
		override fun areItemsTheSame(
			oldItem: Product,
			newItem: Product
		): Boolean {
			Log.d("TAG", "areItemsTheSame: ")
			return oldItem.id == newItem.id
		}

		override fun areContentsTheSame(
			oldItem: Product,
			newItem: Product
		): Boolean {
			return oldItem == newItem
		}

	}
}