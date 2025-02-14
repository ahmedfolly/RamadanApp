package com.example.shopsphere.features.categories.data.mapper

import com.example.shopsphere.common.domain.mapper.IMapper
import com.example.shopsphere.features.categories.data.models.dtos.CategoriesItem
import com.example.shopsphere.features.categories.domain.models.Category

object CategoryMapper: IMapper<Category, CategoriesItem,Unit>() {
	override fun CategoriesItem.fromDtoToDomain(): Category {
		return Category(
			name = name.orEmpty(),
			isSelected = false
		)
	}

}