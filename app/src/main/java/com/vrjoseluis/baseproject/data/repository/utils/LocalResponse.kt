package com.vrjoseluis.baseproject.data.repository.utils

import com.vrjoseluis.baseproject.data.model.AsyncError
import com.vrjoseluis.baseproject.data.remote.util.DataSourceException
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class LocalResponse<ResultType> {

    private var flow: Flow<AsyncResult<ResultType>>? = null
    private var deferredValue: Deferred<AsyncResult<ResultType>>? = null

    //region RepositoryResponse
    private val response = object : RepositoryResponse<ResultType> {
        override suspend fun flow() = executeFlow()
        override suspend fun valueAsync() = executeAsync()
    }
    //endregion

    //region Logic Template
    fun build(): RepositoryResponse<ResultType> {
        return response
    }

    private suspend fun executeFlow(): Flow<AsyncResult<ResultType>> {
        return flow ?: flow {
            executeBase { emit(it) }
        }.apply { flow = this }
    }

    private suspend fun executeAsync(): Deferred<AsyncResult<ResultType>> {
        return deferredValue ?: coroutineScope {
            async {
                executeBase { }
            }
        }.apply { deferredValue = this }
    }

    private suspend fun executeBase(emit: suspend (AsyncResult<ResultType>) -> Unit): AsyncResult<ResultType> {
        emit(AsyncResult.Loading(null))
        val value = try {
            val dbResult = loadFromLocal()
            AsyncResult.Success(dbResult)
        } catch (e: Exception) {
            val asyncError =
                (e as? DataSourceException)?.asyncError ?: AsyncError.UnknownError("An error happened", e)
            AsyncResult.Error(asyncError, null)
        }
        emit(value)
        return value
    }
    //endregion

    //region Abstract methods
    protected abstract suspend fun loadFromLocal(): ResultType?
    //endregion
}
