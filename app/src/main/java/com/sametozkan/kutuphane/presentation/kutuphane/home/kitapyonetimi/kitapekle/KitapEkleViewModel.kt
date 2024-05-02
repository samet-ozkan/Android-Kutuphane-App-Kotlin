package com.sametozkan.kutuphane.presentation.kutuphane.home.kitapyonetimi.kitapekle

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sametozkan.kutuphane.data.datasource.local.sharedpreferences.SessionManager
import com.sametozkan.kutuphane.data.dto.request.KitapKutuphaneReq
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.domain.usecase.kitap.FindKitapByIsbnUseCase
import com.sametozkan.kutuphane.domain.usecase.kitapkutuphane.SaveKitapKutuphaneUseCase
import com.sametozkan.kutuphane.domain.usecase.kutuphane.FindKutuphaneByAccountIdUseCase
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class KitapEkleViewModel @Inject constructor(
    private val findKitapByIsbnUseCase: FindKitapByIsbnUseCase,
    private val sessionManager: SessionManager,
    private val saveKitapKutuphaneUseCase: SaveKitapKutuphaneUseCase,
    private val findKutuphaneByAccountIdUseCase: FindKutuphaneByAccountIdUseCase
) : ViewModel() {

    val changeFragment = MutableLiveData<String>()
    val isbn = MutableLiveData<Long>()
    val stok = MutableLiveData<Long>()
    var kitapId: Long? = null

    fun findByIsbn(onResult: (MyResult<KitapRes>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            isbn.value?.let { isbn ->
                val result = async { findKitapByIsbnUseCase(isbn) }.await()
                withContext(Dispatchers.Main){
                    onResult(result)
                }
            }
        }
    }

    fun saveKitapKutuphane(onResult: (MyResult<Unit>) -> Unit) {
        kitapId?.let { kitap_id ->
            stok.value?.let { stok_sayisi ->
                viewModelScope.launch(Dispatchers.IO) {
                    sessionManager.getAccountID()?.let { account_id ->
                        val kutuphaneResult =
                            async { findKutuphaneByAccountIdUseCase(account_id) }.await()
                        when (kutuphaneResult) {
                            is MyResult.Success -> {
                                val kutuphane_id = kutuphaneResult.data.id
                                val result = async {
                                    saveKitapKutuphaneUseCase(
                                        KitapKutuphaneReq(
                                            kitap_id,
                                            kutuphane_id,
                                            stok_sayisi.toInt()
                                        )
                                    )
                                }.await()
                                withContext(Dispatchers.Main){
                                    onResult(result)
                                }
                            }

                            is MyResult.Error -> {
                                kutuphaneResult.exception.printStackTrace()
                            }
                        }
                    }
                }
            }

        }
    }
}