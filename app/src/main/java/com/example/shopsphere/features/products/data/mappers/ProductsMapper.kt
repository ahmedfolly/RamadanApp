package com.example.shopsphere.features.products.data.mappers

import com.example.shopsphere.common.domain.mapper.IMapper
import com.example.shopsphere.features.products.data.models.dtos.ProductDto
import com.example.shopsphere.features.products.data.models.dtos.ReviewDto
import com.example.shopsphere.features.products.data.models.entities.ProductEntity
import com.example.shopsphere.features.products.data.models.entities.ReviewEntity
import com.example.shopsphere.features.products.domain.models.Product
import com.example.shopsphere.features.products.domain.models.Review
import kotlin.collections.orEmpty

object ProductsMapper : IMapper<Product, ProductDto, ProductEntity>() {
	private fun List<ReviewDto>.toReviewList(): List<Review> {
		return map { it.toReview() }
	}
	private fun ReviewDto.toReview(): Review {
		return Review(
			comment = comment.orEmpty(),
			date = date.orEmpty(),
			rating = rating ?: 0,
			reviewerEmail = reviewerEmail.orEmpty(),
			reviewerName = reviewerName.orEmpty()
		)
	}
	private fun Review.toReviewEntity(): ReviewEntity {
		return ReviewEntity(
			comment = comment.orEmpty(),
			date = date.orEmpty(),
			rating = rating,
			reviewerEmail = reviewerEmail.orEmpty(),
			reviewerName = reviewerName.orEmpty()
		)
	}
	private fun List<Review>.toReviewEntities(): List<ReviewEntity> {
		return map { it.toReviewEntity() }
	}
	private fun ReviewEntity.toReviewEntity(): Review {
		return Review(
			comment = comment,
			date = date,
			rating = rating,
			reviewerEmail = reviewerEmail,
			reviewerName = reviewerName
		)
	}
	private fun List<ReviewEntity>.toReviewDomains(): List<Review> {
		return map { it.toReviewEntity() }
	}
	override fun ProductDto.fromDtoToDomain(): Product {
		return return Product(
			availabilityStatus = availabilityStatus.orEmpty(),
			brand = brand.orEmpty(),
			category = category.orEmpty(),
			description = description.orEmpty(),
			discountPercentage = discountPercentage ?: 0.0,
			id = id ?: 1,
			images = images.orEmpty(),
			price = price ?: 0.0,
			rating = rating ?: 0.0,
			reviews = reviews?.toReviewList().orEmpty(),
			tags = tags.orEmpty(),
			thumbnail = thumbnail.orEmpty(),
			title = title.orEmpty()
		)
	}
	override fun Product.toEntity(): ProductEntity {
		return ProductEntity(
			availabilityStatus = availabilityStatus,
			brand = brand,
			category = category,
			description = description,
			discountPercentage = discountPercentage,
			id = id,
			images = images,
			price = price,
			rating = rating,
			reviews = reviews.toReviewEntities(),
			tags = tags,
			thumbnail = thumbnail,
			title =title
		)
	}
	override fun ProductEntity.fromEntityToDomain(): Product {
		return Product(
			availabilityStatus = availabilityStatus,
			brand = brand,
			category = category,
			description = description,
			discountPercentage = discountPercentage,
			id = id,
			images = images,
			price = price,
			rating = rating,
			reviews = reviews.toReviewDomains(),
			tags = tags,
			thumbnail = thumbnail,
			title =title
		)
	}
}