package com.example.ramadanapp.features.sections.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ramadanapp.databinding.SectionItemLayoutBinding
import com.example.ramadanapp.features.sections.domain.models.Section
import com.example.ramadanapp.features.sections.domain.models.SectionCategory
import com.example.ramadanapp.features.sections.presentation.ui.MainFragmentDirections

class SectionsAdapter() : ListAdapter<Section, SectionsAdapter.SectionsVH>(SectionsDiffUtil()) {
	private lateinit var binding: SectionItemLayoutBinding
	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): SectionsVH {
		binding =
			SectionItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return SectionsVH(binding)
	}

	override fun onBindViewHolder(
		holder: SectionsVH,
		position: Int
	) {
		val sectionItem = getItem(position)
		holder.binds(sectionItem)
	}

	inner class SectionsVH(val binding: SectionItemLayoutBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun binds(section: Section) {
			with(binding) {
				sectionItemMainTitle.text = section.title
				val categoriesAdapter = CategoriesAdapter()
				categoriesAdapter.submitList(section.categories)
				with(categoriesContainer) {
					setHasFixedSize(true)
					layoutManager =
						LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)
					adapter = categoriesAdapter
				}

			}
		}
	}

	class SectionsDiffUtil : DiffUtil.ItemCallback<Section>() {
		override fun areItemsTheSame(
			oldItem: Section,
			newItem: Section
		): Boolean {
			return oldItem.title == newItem.title
		}

		override fun areContentsTheSame(
			oldItem: Section,
			newItem: Section
		): Boolean {
			return oldItem == newItem
		}


	}
}