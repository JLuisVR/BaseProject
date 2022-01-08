package com.vrjoseluis.baseproject.ui.userlist.usecase

import com.vrjoseluis.baseproject.data.model.User
import com.vrjoseluis.baseproject.data.repository.UserRepository
import com.vrjoseluis.baseproject.data.repository.utils.AsyncResult
import kotlinx.coroutines.flow.Flow


interface GetUserListByNameUseCase {
    suspend operator fun invoke(name:String?): Flow<AsyncResult<List<User>>>
}

internal class GetUserListByNameUseCaseImpl(
    private val repository: UserRepository
) : GetUserListByNameUseCase {

    override suspend operator fun invoke(name:String?): Flow<AsyncResult<List<User>>> {
        return repository.getUserListFilterByName(name).flow()
    }

}