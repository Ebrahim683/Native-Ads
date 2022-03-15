package com.example.nativeads.utils

data class Response<out T>(val status: Status, val data: T?, val message: String?) {
	
	companion object {
		fun <T> loading(data: T): Response<T> =
			Response(status = Status.LOADING, data = data, message = null)
		
		fun <T> success(data: T): Response<T> =
			Response(status = Status.SUCCESS, data = data, message = null)
		
		fun <T> error(message: String?): Response<T> =
			Response(status = Status.FAIL, data = null, message = message)
	}
	
}