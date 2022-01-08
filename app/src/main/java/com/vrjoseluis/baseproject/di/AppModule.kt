package com.vrjoseluis.baseproject.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vrjoseluis.baseproject.data.local.AppRoomDatabase
import com.vrjoseluis.baseproject.data.local.UserLocalDataSource
import com.vrjoseluis.baseproject.data.local.UserLocalDataSourceImpl
import com.vrjoseluis.baseproject.data.local.UserDao
import com.vrjoseluis.baseproject.data.remote.UserRemoteDataSource
import com.vrjoseluis.baseproject.data.remote.UserRemoteDataSourceImpl
import com.vrjoseluis.baseproject.data.remote.UserService
import com.vrjoseluis.baseproject.data.repository.UserRepository
import com.vrjoseluis.baseproject.data.repository.UserRepositoryImpl
import com.vrjoseluis.baseproject.ui.userdetail.*
import com.vrjoseluis.baseproject.ui.userdetail.usecase.*
import com.vrjoseluis.baseproject.ui.userdetail.usecase.DeleteUserUserCaseImpl
import com.vrjoseluis.baseproject.ui.userdetail.usecase.GetUserByIdUseCaseImpl
import com.vrjoseluis.baseproject.ui.userdetail.usecase.SaveOrUpdateUserUseCaseImpl
import com.vrjoseluis.baseproject.ui.userlist.usecase.GetUserListByNameUseCase
import com.vrjoseluis.baseproject.ui.userlist.usecase.GetUserListByNameUseCaseImpl
import com.vrjoseluis.baseproject.ui.userlist.usecase.GetUserListUseCase
import com.vrjoseluis.baseproject.ui.userlist.usecase.GetUserListUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //RETROFIT

    @Singleton
    @Provides
    fun provideRetrofit(
        gson: Gson
    ) : Retrofit = Retrofit.Builder()
        .baseUrl("https://hello-world.innocv.com/api/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideUserService(
        retrofit: Retrofit
    ): UserService = retrofit.create(UserService::class.java)


    //ROOM

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ) = AppRoomDatabase.buildDatabase(appContext)

    @Singleton
    @Provides
    fun provideUserDao(
        database: AppRoomDatabase
    ) = database.userDao()


    //DATA SOURCES

    @Singleton
    @Provides
    fun provideUserRemoteDataSource(
        userService: UserService
    ): UserRemoteDataSource = UserRemoteDataSourceImpl(userService)

    @Singleton
    @Provides
    fun provideUserLocalDataSource(
        userDao:UserDao
    ): UserLocalDataSource = UserLocalDataSourceImpl(userDao)


    //REPOSITORIES

    @Singleton
    @Provides
    fun provideUserRepository(
        remote:UserRemoteDataSource,
        local:UserLocalDataSource
    ): UserRepository = UserRepositoryImpl(remote, local)


    //USECASE

    @Provides
    fun getUserListUseCaseProvider(repository: UserRepository) =
        GetUserListUseCaseImpl(repository) as GetUserListUseCase

    @Provides
    fun getUserByIdUseCaseProvider(repository: UserRepository) =
        GetUserByIdUseCaseImpl(repository) as GetUserByIdUseCase

    @Provides
    fun getSaveOrUpdateUserUseCaseProvider(repository: UserRepository) =
        SaveOrUpdateUserUseCaseImpl(repository) as SaveOrUpdateUserUseCase

    @Provides
    fun getDeleteUserUserUseCaseProvider(repository: UserRepository) =
        DeleteUserUserCaseImpl(repository) as DeleteUserUseCase

    @Provides
    fun getUserListByNameUseCaseProvider(repository: UserRepository) =
        GetUserListByNameUseCaseImpl(repository) as GetUserListByNameUseCase
}
