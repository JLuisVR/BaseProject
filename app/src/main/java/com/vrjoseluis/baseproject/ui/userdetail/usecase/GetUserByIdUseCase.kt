package com.vrjoseluis.baseproject.ui.userdetail.usecase

import com.vrjoseluis.baseproject.data.model.User
import com.vrjoseluis.baseproject.data.repository.UserRepository
import com.vrjoseluis.baseproject.data.repository.utils.AsyncResult
import kotlinx.coroutines.flow.Flow


interface GetUserByIdUseCase {
    suspend operator fun invoke(userId: Int): Flow<AsyncResult<User?>>
}

internal class GetUserByIdUseCaseImpl(
    private val repository: UserRepository
) : GetUserByIdUseCase {

    override suspend operator fun invoke(userId:Int): Flow<AsyncResult<User?>> {
        return repository.getUserById(userId).flow()
    }

}