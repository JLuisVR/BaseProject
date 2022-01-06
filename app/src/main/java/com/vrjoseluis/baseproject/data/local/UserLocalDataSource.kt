package com.vrjoseluis.baseproject.data.local

import com.vrjoseluis.baseproject.data.model.User

interface UserLocalDataSource{

    suspend fun getUserById(id: Int): User?
    suspend fun getAllUsers(): List<User>
    suspend fun saveUserList(list: List<User>)
    suspend fun saveUser(user: User)
}

class UserLocalDataSourceImpl(
    private val userDao: UserDao
) : UserLocalDataSource {

    override suspend fun getAllUsers(): List<User> {
        return userDao.getAllUser().map { it.toBo() }
    }

    override suspend fun getUserById(id: Int): User? {
        return userDao.getUserById(id)?.toBo()
    }

    override suspend fun saveUserList(list:List<User>){
        return userDao.insert(list.map { it.toDbo() })
    }

    override suspend fun saveUser(user: User){
        return userDao.insert(user.toDbo())
    }
}