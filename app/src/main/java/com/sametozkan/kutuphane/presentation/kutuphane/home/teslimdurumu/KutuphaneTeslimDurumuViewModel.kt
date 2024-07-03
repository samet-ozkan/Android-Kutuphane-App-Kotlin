package com.sametozkan.kutuphane.presentation.kutuphane.home.teslimdurumu

import android.os.Build
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sametozkan.kutuphane.data.datasource.local.sharedpreferences.SessionManager
import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import com.sametozkan.kutuphane.domain.usecase.kitapkullanici.FindAllKitapKullaniciByKutuphaneIdUseCase
import com.sametozkan.kutuphane.domain.usecase.kitapkullanici.TeslimEdildiUseCase
import com.sametozkan.kutuphane.domain.usecase.kitapkullanici.TeslimEdilmeyenlerUseCase
import com.sametozkan.kutuphane.util.DateUtil
import com.sametozkan.kutuphane.util.KutuphaneKitapIstekleriChips
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class KutuphaneTeslimDurumuViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val teslimEdildiUseCase: TeslimEdildiUseCase,
    private val teslimEdilmeyenlerUseCase: TeslimEdilmeyenlerUseCase
) : ViewModel() {

    var zamaniGecmisCheckbox = ObservableBoolean(false)
    val query = MutableLiveData<String>()
    var kitaplar = ArrayList<KitapKullaniciRes>()
    private val _isEmpty = MutableLiveData(false)
    val isEmpty: LiveData<Boolean> get() = _isEmpty

    fun teslimEdildi(kitapKullaniciId: Long, onResult: (MyResult<Unit>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = async { teslimEdildiUseCase(kitapKullaniciId) }.await()
            withContext(Dispatchers.Main) {
                onResult(result)
            }
        }
    }

    fun fetchTeslimEdilmeyenler(onResult: (MyResult<List<KitapKullaniciRes>>) -> Unit) {
        sessionManager.getAccountID()?.let { account_id ->
            viewModelScope.launch(Dispatchers.IO) {
                val result = async {
                    teslimEdilmeyenlerUseCase(account_id)
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
        var list = this.kitaplar

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

        if(zamaniGecmisCheckbox.get()){
            list = list.filter { kitapKullaniciRes ->
                kitapKullaniciRes.alimTarihi?.let { alimTarihi ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        val localDate = DateUtil.stringToLocalDateTime(alimTarihi) as LocalDateTime
                        localDate.isBefore(LocalDateTime.now())
                    }
                    else{
                        val localDate = DateUtil.stringToLocalDateTime(alimTarihi) as org.threeten.bp.LocalDateTime
                        localDate.isBefore(org.threeten.bp.LocalDateTime.now())
                    }
                } ?: kotlin.run {
                    false
                }
            } as ArrayList<KitapKullaniciRes>
        }

        _isEmpty.value = list.isEmpty()

        return list
    }

}