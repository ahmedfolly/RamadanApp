package com.example.shopsphere.features.products.data.mappers

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.shopsphere.features.products.data.models.entities.ReviewEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {
	val gson = Gson()
	//convert list of Reviews
	@TypeConverter
	fun toReviewsList(data:String):List<ReviewEntity>{
		val type = object : TypeToken<List<ReviewEntity>>() {}.type
		return gson.fromJson(data,type)
	}
	@TypeConverter
	fun fromReviewsList(reviews:List<ReviewEntity>):String{
		return gson.toJson(reviews)
	}
	@TypeConverter
	fun toList(data:String):List<String>{
		val type = object : TypeToken<List<String>>() {}.type
		return gson.fromJson(data,type)
	}
	@TypeConverter
	fun fromList(reviews:List<String>):String{
		return gson.toJson(reviews)
	}
}