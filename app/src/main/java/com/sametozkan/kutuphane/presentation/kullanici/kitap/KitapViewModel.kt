package com.sametozkan.kutuphane.presentation.kullanici.kitap

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sametozkan.kutuphane.data.datasource.local.sharedpreferences.SessionManager
import com.sametozkan.kutuphane.data.dto.request.KitapKullaniciReq
import com.sametozkan.kutuphane.data.dto.request.KitapYorumReq
import com.sametozkan.kutuphane.data.dto.request.KutuphaneYorumReq
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.data.dto.response.KitapYorumRes
import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes
import com.sametozkan.kutuphane.data.dto.response.KutuphaneYorumRes
import com.sametozkan.kutuphane.domain.usecase.kitap.FindKitapByIdUseCase
import com.sametozkan.kutuphane.domain.usecase.kitapkullanici.SaveKitapKullaniciUseCase
import com.sametozkan.kutuphane.domain.usecase.kitapyorum.FindYorumlarByKitapIdUseCase
import com.sametozkan.kutuphane.domain.usecase.kitapyorum.SaveKitapYorumUseCase
import com.sametozkan.kutuphane.domain.usecase.kutuphane.FindKutuphaneByIdUseCase
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class KitapViewModel @Inject constructor(
    private val findKutuphaneByIdUseCase: FindKutuphaneByIdUseCase,
    private val findKitapByIdUseCase: FindKitapByIdUseCase,
    private val saveKitapKullaniciUseCase: SaveKitapKullaniciUseCase,
    private val findYorumlarByKitapIdUseCase: FindYorumlarByKitapIdUseCase,
    private val sessionManager: SessionManager,
    private val saveKitapYorumUseCase: SaveKitapYorumUseCase
) : ViewModel() {

    var kutuphaneId: Long? = null
    var kitapId: Long? = null
    val kutuphane = MutableLiveData<KutuphaneRes>()
    val kitap = MutableLiveData<KitapRes>()
    val yorum = MutableLiveData<String>()

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

    fun fetchKitap(onResult: (MyResult<KitapRes>) -> Unit) {
        kitapId?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val result = async { findKitapByIdUseCase(it) }.await()
                withContext(Dispatchers.Main) {
                    onResult(result)
                }
            }
        } ?: kotlin.run {
            println("Kitap ID is null!")
        }
    }

    fun oduncAl(onResult: (MyResult<Unit>) -> Unit) {
        kutuphaneId?.let { kutuphane_id ->
            kitapId?.let { kitap_id ->
                sessionManager.getAccountID()?.let { account_id ->
                    viewModelScope.launch(Dispatchers.IO) {
                        val result = async {
                            saveKitapKullaniciUseCase(
                                KitapKullaniciReq(
                                    kitap_id,
                                    account_id,
                                    kutuphane_id,
                                    null
                                )
                            )
                        }.await()
                        withContext(Dispatchers.Main) {
                            onResult(result)
                        }
                    }
                }
            }
        }
    }

    fun fetchYorumlar(onResult: (MyResult<List<KitapYorumRes>>) -> Unit) {
        kitapId?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val result = async { findYorumlarByKitapIdUseCase(it) }.await()
                withContext(Dispatchers.Main) {
                    onResult(result)
                }
            }
        } ?: kotlin.run {
            println("Kitap ID is null")
        }
    }

    fun yorumGonder(onResult: (MyResult<Unit>) -> Unit) {
        kitapId?.let { kutuphane_id ->
            sessionManager.getAccountID()?.let { kullanici_id ->
                yorum.value?.let { yorum ->
                    viewModelScope.launch(Dispatchers.IO) {
                        val result = async {
                            saveKitapYorumUseCase(
                                KitapYorumReq(
                                    yorum,
                                    kullanici_id,
                                    kutuphane_id
                                )
                            )
                        }.await()
                        withContext(Dispatchers.Main) {
                            onResult(result)
                        }
                    }
                }
            }
        }
    }

}