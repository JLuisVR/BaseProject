package com.vrjoseluis.baseproject.data.remote

import com.vrjoseluis.baseproject.data.remote.util.RemoteErrorManagement
import com.vrjoseluis.baseproject.data.model.User


interface UserRemoteDataSource {
    suspend fun getAllUsers(): List<User>
    suspend fun getUserById(id: Int): User?
    suspend fun saveUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun deleteUser(userId: Int)
}

class UserRemoteDataSourceImpl(
    private val userService: UserService
) : UserRemoteDataSource {

    override suspend fun getAllUsers() = RemoteErrorManagement.wrap {
        userService.getAllUsers().map { it.toBo() }
    }

    override suspend fun getUserById(id:Int) = RemoteErrorManagement.wrap {
        userService.getUser(id)?.toBo()
    }

    override suspend fun saveUser(user:User) = RemoteErrorManagement.wrap {
        userService.saveUser(user.toDto())
    }

    override suspend fun updateUser(user:User) = RemoteErrorManagement.wrap {
        userService.updateUser(user.toDto())
    }

    override suspend fun deleteUser(userId: Int) = RemoteErrorManagement.wrap {
        userService.deleteUser(userId)
    }

}
