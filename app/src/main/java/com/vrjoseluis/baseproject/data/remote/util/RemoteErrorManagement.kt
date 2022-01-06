package com.vrjoseluis.baseproject.data.remote.util

import com.vrjoseluis.baseproject.data.model.AsyncError
import retrofit2.HttpException
import java.net.UnknownHostException

object RemoteErrorManagement {

    inline fun <T> wrap(block: () -> T): T {
        return try {
            block()
        } catch (e: Throwable) {
            throw DataSourceException(processError(e))
        }
    }

    fun processError(throwable: Throwable): AsyncError {
        return when (throwable) {
            is HttpException -> processRetrofitError(throwable)
            is UnknownHostException -> AsyncError.ConnectionError(throwable.message ?: "Error de conexion")
            else -> AsyncError.UnknownError(throwable.message ?: "Error desconocido", throwable)
        }
    }

    private fun processRetrofitError(httpException: HttpException): AsyncError {
        val errorCode = httpException.code()
        val url = httpException.response()?.raw()?.request()?.url()?.toString() ?: ""
        return AsyncError.ServerError(errorCode, url)
    }
}