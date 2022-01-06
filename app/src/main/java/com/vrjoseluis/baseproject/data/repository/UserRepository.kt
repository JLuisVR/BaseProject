package com.vrjoseluis.baseproject.data.repository

import com.vrjoseluis.baseproject.data.local.UserLocalDataSource
import com.vrjoseluis.baseproject.data.model.User
import com.vrjoseluis.baseproject.data.remote.UserRemoteDataSource
import com.vrjoseluis.baseproject.data.repository.utils.CacheableRemoteResponse
import com.vrjoseluis.baseproject.data.repository.utils.RemoteResponse
import com.vrjoseluis.baseproject.data.repository.utils.RepositoryResponse
import javax.inject.Inject

interface UserRepository {
    suspend fun getAllUsers(forceRefresh: Boolean = false): RepositoryResponse<List<User>>
    suspend fun getUserById(userId: Int, forceRefresh: Boolean = false): RepositoryResponse<User?>
    suspend fun saveUser(user: User): RepositoryResponse<Unit>
    suspend fun updateUser(user: User): RepositoryResponse<Unit>
    suspend fun deleteUser(userId: Int): RepositoryResponse<Unit>
}

internal class UserRepositoryImpl @Inject constructor(
    private val remote: UserRemoteDataSource,
    private val local: UserLocalDataSource
) : UserRepository {

    override suspend fun getAllUsers(forceRefresh: Boolean): RepositoryResponse<List<User>> {
        return object : CacheableRemoteResponse<List<User>>() {
            override suspend fun loadFromLocal(): List<User> {
                return local.getAllUsers()
            }

            override fun shouldRequestFromRemote(localResponse: List<User>): Boolean {
                return forceRefresh || localResponse.isEmpty() || localResponse.any { it.haveToRefreshFromNetwork() }
            }

            override suspend fun requestRemoteCall(): List<User> {
                return remote.getAllUsers()
            }

            override suspend fun saveRemoteResponse(remoteResponse: List<User>) {
                local.saveUserList(remoteResponse)
            }
        }.build()
    }

    override suspend fun getUserById(
        userId: Int,
        forceRefresh: Boolean
    ): RepositoryResponse<User?> {
        return object : CacheableRemoteResponse<User?>() {
            override suspend fun loadFromLocal(): User? {
                return local.getUserById(userId)
            }

            override fun shouldRequestFromRemote(localResponse: User?): Boolean {
                return forceRefresh || localResponse == null || localResponse.haveToRefreshFromNetwork()
            }

            override suspend fun requestRemoteCall(): User? {
                return remote.getUserById(userId)
            }

            override suspend fun saveRemoteResponse(remoteResponse: User?) {
                remoteResponse?.let { local.saveUser(it) }
            }
        }.build()
    }

    override suspend fun deleteUser(userId: Int): RepositoryResponse<Unit> {
        return object : RemoteResponse<Unit>() {
            override suspend fun requestRemoteCall() {
                return remote.deleteUser(userId)
            }
        }.build()
    }

    override suspend fun saveUser(user: User): RepositoryResponse<Unit> {
        return object : RemoteResponse<Unit>() {
            override suspend fun requestRemoteCall() {
                return remote.saveUser(user)
            }
        }.build()
    }

    override suspend fun updateUser(user: User): RepositoryResponse<Unit> {
        return object : RemoteResponse<Unit>() {
            override suspend fun requestRemoteCall() {
                return remote.updateUser(user)
            }
        }.build()
    }
}
