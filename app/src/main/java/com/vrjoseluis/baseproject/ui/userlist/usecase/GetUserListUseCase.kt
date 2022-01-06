package com.vrjoseluis.baseproject.ui.userlist.usecase

import com.vrjoseluis.baseproject.data.model.User
import com.vrjoseluis.baseproject.data.repository.UserRepository
import com.vrjoseluis.baseproject.data.repository.utils.AsyncResult
import kotlinx.coroutines.flow.Flow


interface GetUserListUseCase {
    suspend operator fun invoke(): Flow<AsyncResult<List<User>>>
}

internal class GetUserListUseCaseImpl(
    private val repository: UserRepository
) : GetUserListUseCase {

    override suspend operator fun invoke(): Flow<AsyncResult<List<User>>> {
        return repository.getAllUsers(true).flow()
    }

}