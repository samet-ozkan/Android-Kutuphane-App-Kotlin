package com.sametozkan.kutuphane.presentation.kullanici.home.kitap

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sametozkan.kutuphane.data.datasource.local.sharedpreferences.SessionManager
import com.sametozkan.kutuphane.data.dto.request.KitapKullaniciReq
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes
import com.sametozkan.kutuphane.domain.usecase.kitap.FindKitapByIdUseCase
import com.sametozkan.kutuphane.domain.usecase.kitapkullanici.SaveKitapKullaniciUseCase
import com.sametozkan.kutuphane.domain.usecase.kutuphane.FindKutuphaneByIdUseCase
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class KitapViewModel @Inject constructor(
    private val findKutuphaneByIdUseCase: FindKutuphaneByIdUseCase,
    private val findKitapByIdUseCase: FindKitapByIdUseCase,
    private val saveKitapKullaniciUseCase: SaveKitapKullaniciUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

    var kutuphaneId: Long? = null
    var kitapId: Long? = null
    val kutuphane = MutableLiveData<KutuphaneRes>()
    val kitap = MutableLiveData<KitapRes>()

    fun fetchKutuphane(onResult: (MyResult<KutuphaneRes>) -> Unit) {
        kutuphaneId?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val result = findKutuphaneByIdUseCase(it)
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
                val result = findKitapByIdUseCase(it)
                withContext(Dispatchers.Main) {
                    onResult(result)
                }
            }
        } ?: kotlin.run {
            println("Kitap ID is null!")
        }
    }

    fun oduncAl(onResult: (MyResult<Unit>) -> Unit){
        kutuphaneId?.let { kutuphane_id ->
            kitapId?.let {
                kitap_id ->
                sessionManager.getAccountID()?.let { account_id ->
                    viewModelScope.launch(Dispatchers.IO) {
                        val result = saveKitapKullaniciUseCase(KitapKullaniciReq(kitap_id, account_id, kutuphane_id, null))
                        withContext(Dispatchers.Main) {
                            onResult(result)
                        }
                    }
                }
            }
        }
    }
}