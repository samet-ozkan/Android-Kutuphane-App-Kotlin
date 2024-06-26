package com.sametozkan.kutuphane.presentation.kutuphane.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sametozkan.kutuphane.data.datasource.local.sharedpreferences.SessionManager
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes
import com.sametozkan.kutuphane.domain.usecase.kutuphane.FindKutuphaneByAccountIdUseCase
import com.sametozkan.kutuphane.util.MyResult
import com.sametozkan.kutuphane.domain.usecase.kutuphane.FindKutuphaneByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class KutuphaneHomeViewModel @Inject constructor(
    private val findKutuphaneByAccountIdUseCase: FindKutuphaneByAccountIdUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

    val changeFragment: MutableLiveData<String> = MutableLiveData<String>()

    fun fetchKutuphane(onResult : (MyResult<KutuphaneRes>) -> Unit) {
        val accountId = sessionManager.getAccountID()
        accountId?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val result = async { findKutuphaneByAccountIdUseCase(accountId) }.await()
                withContext(Dispatchers.Main){
                    onResult(result)
                }
            }
        } ?: run { println("Account ID null!") }
    }

    fun logout(){
        sessionManager.clear()
    }
}