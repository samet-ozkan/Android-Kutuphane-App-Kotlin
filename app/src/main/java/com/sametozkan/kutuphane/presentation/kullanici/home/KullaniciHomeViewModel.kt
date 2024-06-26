package com.sametozkan.kutuphane.presentation.kullanici.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sametozkan.kutuphane.data.datasource.local.sharedpreferences.SessionManager
import com.sametozkan.kutuphane.data.dto.response.KullaniciRes
import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes
import com.sametozkan.kutuphane.domain.usecase.kullanici.FindKullaniciByAccountIdUseCase
import com.sametozkan.kutuphane.domain.usecase.kutuphane.FindKutuphaneByAccountIdUseCase
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class KullaniciHomeViewModel @Inject constructor(private val sessionManager: SessionManager,
private val findKullaniciByAccountIdUseCase: FindKullaniciByAccountIdUseCase
) : ViewModel() {

    val changeFragment = MutableLiveData<String>()

    fun fetchKullanici(onResult : (MyResult<KullaniciRes>) -> Unit) {
        val accountId = sessionManager.getAccountID()
        accountId?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val result = async { findKullaniciByAccountIdUseCase(accountId) }.await()
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