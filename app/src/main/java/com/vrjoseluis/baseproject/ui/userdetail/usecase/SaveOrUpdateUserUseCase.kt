package com.vrjoseluis.baseproject.ui.userdetail.usecase

import com.vrjoseluis.baseproject.data.model.User
import com.vrjoseluis.baseproject.data.repository.UserRepository
import com.vrjoseluis.baseproject.data.repository.utils.AsyncResult
import kotlinx.coroutines.flow.Flow


interface SaveOrUpdateUserUseCase {
    suspend operator fun invoke(user: User): Flow<AsyncResult<Unit>>
}

internal class SaveOrUpdateUserUseCaseImpl(
    private val repository: UserRepository
) : SaveOrUpdateUserUseCase {

    override suspend operator fun invoke(user: User): Flow<AsyncResult<Unit>> {
        return if (user.id == null) {
            repository.saveUser(user).flow()
        } else {
            repository.updateUser(user).flow()
        }
    }

}