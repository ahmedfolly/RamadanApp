package com.example.shopsphere.features.products.data.models.entities

data class ReviewEntity(
    val comment: String,
    val date: String,
    val rating: Int,
    val reviewerEmail: String,
    val reviewerName: String
)