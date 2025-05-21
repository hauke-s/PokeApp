package de.haukeschebitz.pokeapp.common

sealed class Error {
    data object NoInternet : Error()
    data class ApiError(val code: Int, val message: String) : Error()
    data class UnknownError(val throwable: Throwable) : Error()
}
