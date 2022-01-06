package com.vrjoseluis.baseproject.data.remote

import androidx.room.Delete
import com.vrjoseluis.baseproject.data.remote.dto.UserDto
import retrofit2.Response
import retrofit2.http.*

interface UserService {
    @GET("User")
    suspend fun getAllUsers() : List<UserDto>

    @GET("User/{id}")
    suspend fun getUser(@Path("id") id: Int): UserDto?

    @POST("User")
    suspend fun saveUser(@Body user:UserDto)

    @PUT("User")
    suspend fun updateUser(@Body user:UserDto)

    @DELETE("User/{id}")
    suspend fun deleteUser(@Path("id") id: Int)
}