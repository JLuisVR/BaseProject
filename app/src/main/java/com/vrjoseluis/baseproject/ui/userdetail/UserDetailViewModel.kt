package com.vrjoseluis.baseproject.ui.userdetail;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.vrjoseluis.baseproject.data.model.User
import com.vrjoseluis.baseproject.data.repository.utils.AsyncResult
import com.vrjoseluis.baseproject.ui.userdetail.usecase.DeleteUserUseCase
import com.vrjoseluis.baseproject.ui.userdetail.usecase.GetUserByIdUseCase
import com.vrjoseluis.baseproject.ui.userdetail.usecase.SaveOrUpdateUserUseCase
import com.vrjoseluis.baseproject.util.MutableSourceLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val saveOrUpdateUserUseCase: SaveOrUpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    private val userLiveData = MutableSourceLiveData<AsyncResult<User?>>()
    private val saveUserLiveData = MutableSourceLiveData<AsyncResult<Unit>>()
    private val deleteUserLiveData = MutableSourceLiveData<AsyncResult<Unit>>()

    fun getUserDetailLiveData() = userLiveData.liveData()
    fun getSaveUserLiveData() = saveUserLiveData.liveData()
    fun getDeleteUserLiveData() = deleteUserLiveData.liveData()

    fun requestUserDetail(userId:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            userLiveData.changeSource(getUserByIdUseCase(userId).asLiveData(coroutineContext))
        }
    }

    fun saveUser(user:User) {
        viewModelScope.launch(Dispatchers.IO) {
            saveUserLiveData.changeSource(saveOrUpdateUserUseCase(user).asLiveData(coroutineContext))
        }
    }

    fun deleteUser(userId:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteUserLiveData.changeSource(deleteUserUseCase(userId).asLiveData(coroutineContext))
        }
    }
}
