package com.vrjoseluis.baseproject.ui.userdetail.usecase

import com.vrjoseluis.baseproject.data.repository.UserRepository
import com.vrjoseluis.baseproject.data.repository.utils.AsyncResult
import kotlinx.coroutines.flow.Flow


interface DeleteUserUseCase {
    suspend operator fun invoke(userId: Int): Flow<AsyncResult<Unit>>
}

internal class DeleteUserUserCaseImpl(
    private val repository: UserRepository
) : DeleteUserUseCase {

    override suspend operator fun invoke(userId: Int): Flow<AsyncResult<Unit>> {
        return repository.deleteUser(userId).flow()
    }

}