package com.sametozkan.kutuphane.presentation.kutuphane.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sametozkan.kutuphane.data.datasource.local.sharedpreferences.SessionManager
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes
import com.sametozkan.kutuphane.util.MyResult
import com.sametozkan.kutuphane.domain.usecase.kutuphane.FindKutuphaneByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KutuphaneHomeViewModel @Inject constructor(
    private val findKutuphaneByAccountIdUseCase: FindKutuphaneByIdUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

    val changeFragment: MutableLiveData<String> = MutableLiveData<String>()
    val kutuphane: MutableLiveData<KutuphaneRes> = MutableLiveData<KutuphaneRes>()

    init {
        fetchKutuphane()
    }

    private fun fetchKutuphane() {
        val accountId = sessionManager.getAccountID()
        accountId?.let {
            viewModelScope.launch(Dispatchers.IO) {
                when (val result = findKutuphaneByAccountIdUseCase(accountId)) {
                    is MyResult.Success -> this@KutuphaneHomeViewModel.kutuphane.postValue(result.data)
                    is MyResult.Error -> println(result.exception.message)
                }
            }
        } ?: run { println("Account ID null!") }
    }
}