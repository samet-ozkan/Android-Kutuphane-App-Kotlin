package com.sametozkan.kutuphane.presentation.kullanici.home.kutuphane

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sametozkan.kutuphane.data.datasource.local.sharedpreferences.SessionManager
import com.sametozkan.kutuphane.data.dto.request.KutuphaneYorumReq
import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes
import com.sametozkan.kutuphane.data.dto.response.KutuphaneYorumRes
import com.sametozkan.kutuphane.domain.usecase.kutuphane.FindKutuphaneByIdUseCase
import com.sametozkan.kutuphane.domain.usecase.kutuphaneyorum.FindYorumlarByKutuphaneIdUseCase
import com.sametozkan.kutuphane.domain.usecase.kutuphaneyorum.SaveKutuphaneYorumUseCase
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class KutuphaneViewModel @Inject constructor(
    private val findKutuphaneByIdUseCase: FindKutuphaneByIdUseCase,
    private val findYorumlarByKutuphaneIdUseCase: FindYorumlarByKutuphaneIdUseCase,
    private val saveKutuphaneYorumUseCase: SaveKutuphaneYorumUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

    var kutuphaneId: Long? = null
    var yorum = MutableLiveData<String>()

    fun fetchKutuphane(onResult: (MyResult<KutuphaneRes>) -> Unit) {
        kutuphaneId?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val result = async { findKutuphaneByIdUseCase(it) }.await()
                withContext(Dispatchers.Main) {
                    onResult(result)
                }
            }
        } ?: kotlin.run {
            println("Kutuphane ID is null!")
        }
    }

    fun fetchYorumlar(onResult: (MyResult<List<KutuphaneYorumRes>>) -> Unit) {
        kutuphaneId?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val result = async { findYorumlarByKutuphaneIdUseCase(it) }.await()
                withContext(Dispatchers.Main) {
                    onResult(result)
                }
            }
        } ?: kotlin.run {
            println("Kutuphane ID is null")
        }
    }

    fun yorumGonder(onResult: (MyResult<Unit>) -> Unit) {
        kutuphaneId?.let { kutuphane_id ->
            sessionManager.getAccountID()?.let { kullanici_id ->
                yorum.value?.let { yorum ->
                    viewModelScope.launch(Dispatchers.IO) {
                        val result = async { saveKutuphaneYorumUseCase(
                            KutuphaneYorumReq(
                                yorum,
                                kullanici_id,
                                kutuphane_id
                            )
                        ) }.await()
                        withContext(Dispatchers.Main){
                            onResult(result)
                        }
                    }
                }
            }
        }
    }
}