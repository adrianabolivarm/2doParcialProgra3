package com.example.a2parciall.data.remote

/**
 * Clase sellada envolvente de resultados para manejar estados Exito, Error y Carga
 * en las peticiones de red y repositorios.
 */
sealed interface ResultState<out T> {
    object Loading : ResultState<Nothing>
    data class Success<out T>(val data: T) : ResultState<T>
    data class Error(val message: String, val throwable: Throwable? = null) : ResultState<Nothing>
}
