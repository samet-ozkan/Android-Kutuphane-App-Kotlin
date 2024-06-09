package com.sametozkan.kutuphane.presentation.kullanici.home.kitaponerileri

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sametozkan.kutuphane.data.datasource.local.sharedpreferences.SessionManager
import com.sametozkan.kutuphane.data.dto.response.KitapOnerisiRes
import com.sametozkan.kutuphane.domain.usecase.kitapkullanici.FetchKitapOnerileriUseCase
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class KitapOnerileriViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val fetchKitapOnerileriUseCase: FetchKitapOnerileriUseCase
) : ViewModel() {

    val kitapOnerileri = MutableLiveData<List<KitapOnerisiRes>>()

    fun fetchKitapOnerileri(onResult: (MyResult<List<KitapOnerisiRes>>) -> Unit) {
        sessionManager.getAccountID()?.let { account_id ->
            viewModelScope.launch(Dispatchers.IO) {
                val result = async { fetchKitapOnerileriUseCase(account_id) }.await()
                withContext(Dispatchers.Main) {
                    onResult(result)
                }
            }
        }
    }
    
}