package com.example.ramadanapp.common.data.repos.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface RamadanAppService {
	@POST("{endPoint}")
	suspend fun post(
		@Path("endPoint", encoded = true)endPoint: String,
		@HeaderMap headers: Map<String, String>?=null,
		@Body body: Any?=null,
		@QueryMap queries: Map<String, String>?=null
	): Response<ResponseBody>

	@GET("{endPoint}")
	suspend fun get(
		@Path("endPoint", encoded = true) endPoint: String,
		@HeaderMap headers: Map<String, String>?=null,
		@QueryMap queries: Map<String, String>?=null
	): Response<ResponseBody>

	//endpoint = "products/category/$categoryName"
	@POST("{endPoint}")
	suspend fun put(
		@Path("endPoint", encoded = true) endPoint: String,
		@HeaderMap headers: Map<String, Any>?,
		@Body body: Any?,
		@QueryMap queries: Map<String, Any>?
	): Response<ResponseBody>

	@POST("{endPoint}")
	suspend fun delete(
		@Path("endPoint", encoded = true) endPoint: String,
		@HeaderMap headers: Map<String, Any>?,
		@Body body: Any?,
		@QueryMap queries: Map<String, Any>?
	): Response<ResponseBody>
}