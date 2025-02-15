package com.example.ramadanapp.common.data.repos.remote

import android.util.Log
import com.example.ramadanapp.common.domain.models.Constants
import com.example.ramadanapp.common.domain.models.RamadanAppException
import com.example.ramadanapp.common.domain.repos.remote.IRemoteProvider
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.lang.reflect.Type

@Suppress("UNCHECKED_CAST")
class RetrofitProvider(private val service: RamadanAppService, private val gson: Gson) :
	IRemoteProvider {
	override suspend fun <Return> post(
		endPoint: String,
		headers: Map<String, String>?,
		queries: Map<String, String>?,
		body: Any?,
		returnType: Type
	): Return {
		return try {
			val postResponse =
				service.post(endPoint = endPoint, headers = headers, queries = queries, body = body)
			handleRequest(postResponse, returnType)
		} catch (e: HttpException) {
			handleHttpException(e)
		} catch (e: Exception) {
			throw RamadanAppException.UnknownException("Unknown error occurred")
		}
	}

	override suspend fun <Return> get(
		endPoint: String,
		headers: Map<String, String>?,
		queries: Map<String, String>?,
		returnType: Type
	): Return {
		return try {
			val getResponse = service.get(
				endPoint = endPoint,
				headers = headers ?: emptyMap(),
				queries = queries ?: emptyMap()
			)
			gson.fromJson(getResponse.body()?.string(), returnType)
		} catch (e: HttpException) {
			handleHttpException(e)
		} catch (e: Exception) {
			throw RamadanAppException.UnknownException("Unknown error occurred ${e.message}")
		}
	}

	override suspend fun <Return> put(
		endPoint: String,
		headers: Map<String, Any>?,
		queries: Map<String, Any>?,
		body: Any?,
		returnType: Type

	): Return {
		TODO("Not yet implemented")
	}

	override suspend fun <Return> delete(
		endPoint: String,
		headers: Map<String, Any>?,
		queries: Map<String, Any>?,
		body: Any?,
		returnType: Type
	): Return {
		TODO("Not yet implemented")
	}

	private fun handleHttpException(e: HttpException): Nothing {
		val code = e.code()
		val msg = e.message
		throw when (code) {
			Constants
				.UNAUTHORIZED -> RamadanAppException
				.HTTPException
				.Unauthorized(
					code,
					msg
				)

			Constants
				.FORBIDDEN -> RamadanAppException
				.HTTPException
				.Forbidden(
					code,
					msg
				)

			Constants
				.NOTFOUND -> RamadanAppException
				.HTTPException
				.NotFound(
					code,
					msg
				)

			Constants.BADREQUEST -> {
				Log.d("TAG", "handleHttpException: ${e.message}")
				RamadanAppException
					.HTTPException
					.BadRequest(code, msg.orEmpty())
			}

			else -> RamadanAppException
				.HTTPException
				.UnKnownCode(
					code,
					msg
				)
		}
	}

	private fun <T> handleRequest(postResponse: Response<ResponseBody>, returnType: Type): T {
		return if (postResponse.isSuccessful) {
			if (postResponse.body() != null) {
				val body = postResponse.body()?.string()
				Log.d("TAG", "post: $body")
			}
			if (postResponse.errorBody() != null) {
				Log.d("TAG", "post: ${postResponse.errorBody()?.string()}")
			}
			gson.fromJson(postResponse.body()?.string(), returnType)
		} else {
			throw RamadanAppException.UnknownException("Unknown error happened")
		}
	}
}