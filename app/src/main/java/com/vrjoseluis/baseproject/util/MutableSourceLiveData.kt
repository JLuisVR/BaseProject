package com.vrjoseluis.baseproject.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MutableSourceLiveData<Type> : Observer<Type> {

    //region Fields
    private val mediatorLiveData = MediatorLiveData<Type?>()
    private var actualSource: LiveData<Type?> = MutableLiveData<Type>()
    //endregion

    init {
        mediatorLiveData.addSource(actualSource){
            mediatorLiveData.postValue(it)
        }
    }

    //region Public methods
    fun changeSource(source: LiveData<Type>) {
        GlobalScope.launch(Dispatchers.Main) {
            mediatorLiveData.removeSource(actualSource)
            mediatorLiveData.addSource(source, this@MutableSourceLiveData)
        }
    }

    fun liveData() = mediatorLiveData as LiveData<Type?>
    //endregion

    //region override Observer<Type>
    override fun onChanged(value: Type?) {
        mediatorLiveData.postValue(value)
    }
    //endregion
}