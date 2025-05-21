package de.haukeschebitz.pokeapp.network

sealed class ApiResponse<out T> {

    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val exception: Exception, val message: String? = null) : ApiResponse<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception, message=$message]"
        }
    }
}

inline fun <T> ApiResponse<T>.onSuccess(action: (data: T) -> Unit): ApiResponse<T> {
    if (this is ApiResponse.Success) {
        action(data)
    }
    return this
}

inline fun <T> ApiResponse<T>.onFailure(action: (exception: Exception, message: String?) -> Unit): ApiResponse<T> {
    if (this is ApiResponse.Error) {
        action(exception, message)
    }
    return this
}

inline fun <T, R> ApiResponse<T>.fold(
    onSuccess: (data: T) -> R,
    onFailure: (exception: Exception, message: String?) -> R
): R {
    return when (this) {
        is ApiResponse.Success -> onSuccess(this.data)
        is ApiResponse.Error -> onFailure(this.exception, this.message)
    }
}

