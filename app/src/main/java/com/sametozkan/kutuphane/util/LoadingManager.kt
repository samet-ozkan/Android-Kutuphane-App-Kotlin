package com.sametozkan.kutuphane.util

import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map


object LoadingManager {

    private val _loadingCount = MutableLiveData<Int>().apply { postValue(0) }
    val loading: LiveData<Boolean> = _loadingCount.map { count ->
        count > 0
    }

    @Synchronized
    fun startLoading() {
        val currentCount = _loadingCount.value ?: 0
        _loadingCount.postValue(currentCount + 1)
    }

    @Synchronized
    fun stopLoading() {
        val currentCount = _loadingCount.value ?: 0
        _loadingCount.postValue((currentCount - 1).coerceAtLeast(0))
    }
}
