package com.example.shopsphere.common.domain.repos.remote

import java.lang.reflect.Type

interface IRemoteProvider {
	suspend fun<Return> post(
		endPoint:String,
		headers: Map<String,String>?=null,
		queries: Map<String,String>?=null,
		body:Any?,
		returnType: Type
	):Return?
	suspend fun<Return> get(
		endPoint:String,
		headers: Map<String,String>?=null,
		queries: Map<String,String>?=null,
		returnType: Type
	):Return
	suspend fun<Return> put(
		endPoint:String,
		headers: Map<String,Any>?=null,
		queries: Map<String,Any>?=null,
		body:Any?,
		returnType: Type
	):Return
	suspend fun<Return> delete(
		endPoint:String,
		headers: Map<String,Any>?=null,
		queries: Map<String,Any>?=null,
		body:Any?,
		returnType: Type
	):Return
}