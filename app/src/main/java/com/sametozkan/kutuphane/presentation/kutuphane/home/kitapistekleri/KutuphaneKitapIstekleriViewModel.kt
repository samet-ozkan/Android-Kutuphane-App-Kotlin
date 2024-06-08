package com.sametozkan.kutuphane.presentation.kutuphane.home.kitapistekleri

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sametozkan.kutuphane.data.datasource.local.sharedpreferences.SessionManager
import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import com.sametozkan.kutuphane.domain.usecase.kitapkullanici.FindAllKitapKullaniciByKutuphaneIdUseCase
import com.sametozkan.kutuphane.domain.usecase.kitapkullanici.KitapIstegiOnaylaUseCase
import com.sametozkan.kutuphane.domain.usecase.kitapkullanici.KitapIstegiReddetUseCase
import com.sametozkan.kutuphane.util.KutuphaneKitapIstekleriChips
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class KutuphaneKitapIstekleriViewModel @Inject constructor(
    private val findAllKitapKullaniciByKutuphaneId: FindAllKitapKullaniciByKutuphaneIdUseCase,
    private val sessionManager: SessionManager,
    private val kitapIstegiOnaylaUseCase: KitapIstegiOnaylaUseCase,
    private val kitapIstegiReddetUseCase: KitapIstegiReddetUseCase
) : ViewModel() {

    val query = MutableLiveData<String>()
    var istekler = ArrayList<KitapKullaniciRes>()
    val chips = MutableLiveData<ArrayList<KutuphaneKitapIstekleriChips>>()

    fun kitapIstegiOnayla(kitapKullaniciId: Long, onResult: (MyResult<Unit>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = async { kitapIstegiOnaylaUseCase(kitapKullaniciId) }.await()
            withContext(Dispatchers.Main) {
                onResult(result)
            }
        }
    }

    fun kitapIstegiReddet(kitapKullaniciId: Long, onResult: (MyResult<Unit>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = async { kitapIstegiReddetUseCase(kitapKullaniciId) }.await()
            withContext(Dispatchers.Main) {
                onResult(result)
            }
        }
    }

    fun fetchIstekler(onResult: (MyResult<List<KitapKullaniciRes>>) -> Unit) {
        sessionManager.getAccountID()?.let { account_id ->
            viewModelScope.launch(Dispatchers.IO) {
                val result = async {
                    findAllKitapKullaniciByKutuphaneId(account_id)
                }.await()
                withContext(Dispatchers.Main) {
                    onResult(result)
                }
            }
        } ?: kotlin.run {
            println("Account ID is null!")
        }
    }

    fun filter(): ArrayList<KitapKullaniciRes> {
        var list = this.istekler

        this.query.value?.let { query ->
            if (query != null && !query.isEmpty()) {
                list = list.filter {
                    it.kullanici.id.toString() == query || "${it.kullanici.adi} ${it.kullanici.soyadi}".contains(
                        query,
                        ignoreCase = true
                    )
                } as ArrayList<KitapKullaniciRes>
            }
        }

        this.chips.value?.let { chips ->
            if (chips != null && !chips.isEmpty()) {
                val filterBekleyen = chips.contains(KutuphaneKitapIstekleriChips.BEKLEYEN)
                val filterOnaylandi = chips.contains(KutuphaneKitapIstekleriChips.ONAYLANDI)
                val filterReddedildi = chips.contains(KutuphaneKitapIstekleriChips.REDDEDILDI)
                list = list.filter { kitapKullaniciRes ->
                    when {
                        filterBekleyen && kitapKullaniciRes.onaylandi == null -> true
                        filterOnaylandi && kitapKullaniciRes.onaylandi == true -> true
                        filterReddedildi && kitapKullaniciRes.onaylandi == false -> true
                        else -> false
                    }
                } as ArrayList<KitapKullaniciRes>
            }
        }

        return list
    }

}