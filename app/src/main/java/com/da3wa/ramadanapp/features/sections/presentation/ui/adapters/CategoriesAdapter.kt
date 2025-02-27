package com.da3wa.ramadanapp.features.sections.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.edu.da3wa.ramadanapp.databinding.CategoryItemLayoutBinding
import com.da3wa.ramadanapp.features.sections.domain.models.SectionCategory
import com.da3wa.ramadanapp.features.sections.presentation.ui.MainFragmentDirections

class CategoriesAdapter : ListAdapter<SectionCategory, CategoriesAdapter.CategoriesVH> (SectionsDiffUtil()) {
	private lateinit var binding: CategoryItemLayoutBinding
	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): CategoriesVH {
		binding = CategoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
		return CategoriesVH(binding)
	}

	override fun onBindViewHolder(
		holder: CategoriesVH,
		position: Int
	) {
		val sectionItem = getItem(position)
		holder.binds(sectionItem)
	}

	inner class CategoriesVH(val binding: CategoryItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
		fun binds(section: SectionCategory){
			with(binding){
				categoryItemImg.load(section.imageUrl){
					crossfade(true)
				}
				categoryItemTitle.text = section.title
				with(root){
					setOnClickListener{
						val action = MainFragmentDirections.actionMainFragmentToPlaylistFragment(section.title)
						findNavController().navigate(action)
					}
				}
			}
		}
	}
	class SectionsDiffUtil: DiffUtil.ItemCallback<SectionCategory>(){
		override fun areItemsTheSame(
			oldItem: SectionCategory,
			newItem: SectionCategory
		): Boolean {
			return oldItem.title == newItem.title
		}

		override fun areContentsTheSame(
			oldItem: SectionCategory,
			newItem: SectionCategory
		): Boolean {
			return oldItem == newItem
		}

	}
}