package com.example.shopsphere.features.categories.presentation.adatper

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shopsphere.databinding.CategoryItemBinding
import com.example.shopsphere.features.categories.domain.models.Category
import kotlin.properties.Delegates
import com.example.shopsphere.R

class CategoriesAdapter(val onCategoryClicked: OnCategoryClicked) :
	ListAdapter<Category, CategoriesAdapter.CategoriesVH>(CategoriesDiffUtil()) {
	private lateinit var _binding: CategoryItemBinding
	private val binding
		get() = _binding

	var selectedCategoryPosition by Delegates.observable(-1) { _, oldPos, newPos ->
		if (oldPos != -1) {
			notifyItemChanged(oldPos)
		}
		if (newPos != -1) {
			notifyItemChanged(newPos)
		}
	}

	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): CategoriesVH {
		_binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return CategoriesVH(binding)
	}

	override fun onBindViewHolder(
		holder: CategoriesVH,
		position: Int
	) {
		val category = getItem(position)
		val isSelected = position == selectedCategoryPosition
		category.isSelected = isSelected
		holder.binds(category)
	}

	inner class CategoriesVH(val binding: CategoryItemBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun binds(category: Category) {
			with(binding) {
				val categoryName = category.name
				val categoryBack =
					if (category.isSelected) R.drawable.category_selected_bckg else R.drawable.category_back
				with(tvcategoryName) {
					text = categoryName
					setBackgroundResource(categoryBack)
				}
				root.setOnClickListener {
					selectedCategoryPosition = bindingAdapterPosition
					onCategoryClicked.onClick(categoryName)
				}
			}
		}

	}

	interface OnCategoryClicked {
		fun onClick(categoryName: String)
	}

	class CategoriesDiffUtil : DiffUtil.ItemCallback<Category>() {
		override fun areItemsTheSame(
			oldItem: Category,
			newItem: Category
		): Boolean {
			Log.d("TAG", "Comparing items: ${oldItem.name} vs ${newItem.name}")
			return oldItem.name == newItem.name
		}

		override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
			Log.d("TAG", "Comparing contents: ${oldItem} vs ${newItem}")
			return oldItem == newItem
		}

	}
}