package com.sametozkan.kutuphane.presentation.kutuphane.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sametozkan.kutuphane.data.datasource.local.sharedpreferences.SessionManager
import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes
import com.sametozkan.kutuphane.domain.usecase.MyResult
import com.sametozkan.kutuphane.domain.usecase.kutuphane.FindKutuphaneByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KutuphaneHomeViewModel @Inject constructor(
    private val findKutuphaneByAccountIdUseCase: FindKutuphaneByIdUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

    val changeFragment: MutableLiveData<String> = MutableLiveData<String>()

    fun getKutuphane(onResult: (MyResult<KutuphaneRes>) -> Unit) {
        val accountId = sessionManager.getAccountID()
        accountId?.let {
            viewModelScope.launch {
                onResult(findKutuphaneByAccountIdUseCase(accountId))
            }
        } ?: run { println("Account ID null!") }

    }
}