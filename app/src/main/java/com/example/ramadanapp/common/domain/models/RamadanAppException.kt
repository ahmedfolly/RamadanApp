package com.example.ramadanapp.common.domain.models

sealed class RamadanAppException(errorMessage: String) : Exception(errorMessage) {
	// Server-related exceptions
	sealed class ServerException(message: String) : RamadanAppException(message) {
		class ServerErrorException : ServerException("There is a problem, please return again")
		class UserBlockException : ServerException("You are blocked")
	}

	// Network-related exceptions
	sealed class NetworkException(override val message: String?) :
		RamadanAppException(message.orEmpty()) {
		class NetworkErrorException :
			NetworkException("Unable to connect to the network. Please try again later")

		class SocketTimeoutException(override val message: String?) :
			NetworkException(message.orEmpty())

		class ConnectException(override val message: String) : NetworkException(message)
		class UnknownHostException(override val message: String) : NetworkException(
			message
		)

		class IOException(override val message: String) : NetworkException(message)
		class EmptyResponseBodyException(override val message: String) : NetworkException(
			message
		)

		class FailedRequest(override val message: String) : NetworkException(message)
	}

	// Authentication-related exceptions
	sealed class AuthenticationException(message: String) : RamadanAppException(message) {
		class InvalidCredentialsException : AuthenticationException("Invalid email")
		class InvalidPhoneNumber : AuthenticationException("Invalid phone")
		class RequestValidation(val errors: Map<String, String> = hashMapOf<String, String>()) :
			AuthenticationException("")
	}

	// Local storage exceptions
	sealed class StorageException(message: String) : RamadanAppException(message) {
		class LocalStorageException(override val message: String? = "") :
			StorageException("There is a problem with reading stored data")

		class UnknownPreferencesType : StorageException("Unknown type")
		class NotFoundData(override val message: String? = "Not found") :
			StorageException("Not found data")

		class UnSupportedType : StorageException("Un supported type")
	}

	sealed class HTTPException(open val code: Int, message: String) : RamadanAppException(message) {
		class Unauthorized(override val code: Int, override val message: String?) : HTTPException(
			code, message.orEmpty()
		)

		class Forbidden(override val code: Int, override val message: String?) : HTTPException(
			code, message.orEmpty()
		)

		class NotFound(override val code: Int, override val message: String?) : HTTPException(
			code, message.orEmpty()
		)

		class BadRequest(override val code: Int, override val message: String) : HTTPException(
			code, message
		)

		class UnKnownCode(override val code: Int, override val message: String?) : HTTPException(
			code, message.orEmpty()
		)
	}

	sealed class SerializationException(override val message: String) :
		RamadanAppException(message) {
		class JsonSyntaxException(override val message: String) : SerializationException(
			message
		)

		class JsonParseException(override val message: String) : SerializationException(
			message
		)

		class IllegalStateException(override val message: String) : SerializationException(
			message
		)
	}

	class UnknownException(override val message: String? = "Unknown") :
		RamadanAppException(message.orEmpty())
}