package com.vrjoseluis.baseproject.data.repository.utils

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

interface RepositoryResponse<out ResultType> {

    suspend fun flow(): Flow<AsyncResult<ResultType>>

    suspend fun valueAsync(): Deferred<AsyncResult<ResultType>>

}

fun <ResultType> emptyRepositoryResponse(): RepositoryResponse<ResultType> = EmptyRepositoryResponse

private object EmptyRepositoryResponse : RepositoryResponse<Nothing> {
    override suspend fun flow(): Flow<AsyncResult<Nothing>> {
        return emptyFlow()
    }

    override suspend fun valueAsync(): Deferred<AsyncResult<Nothing>> {
        return CompletableDeferred()
    }
}