package com.vrjoseluis.baseproject.ui.userlist;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.vrjoseluis.baseproject.data.model.User
import com.vrjoseluis.baseproject.data.repository.utils.AsyncResult
import com.vrjoseluis.baseproject.ui.userlist.usecase.GetUserListUseCase
import com.vrjoseluis.baseproject.util.MutableSourceLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase
) : ViewModel() {

    private val userListLiveData = MutableSourceLiveData<AsyncResult<List<User>>>()

    fun getUserListLiveData() = userListLiveData.liveData()

    fun requestCharacterList() {
        viewModelScope.launch(Dispatchers.IO) {
            userListLiveData.changeSource(getUserListUseCase().asLiveData(coroutineContext))
        }
    }
}
