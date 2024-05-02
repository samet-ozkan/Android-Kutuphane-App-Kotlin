package com.sametozkan.kutuphane.presentation.kutuphane.home.kitapyonetimi.kitapekle.kitapolustur

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sametozkan.kutuphane.data.datasource.local.sharedpreferences.SessionManager
import com.sametozkan.kutuphane.data.dto.request.KitapKutuphaneReq
import com.sametozkan.kutuphane.data.dto.request.KitapReq
import com.sametozkan.kutuphane.domain.usecase.kitap.FetchKitapByIsbnUseCase
import com.sametozkan.kutuphane.domain.usecase.kitap.SaveKitapUseCase
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
class KitapOlusturViewModel @Inject constructor(
    private val fetchKitapByIsbnUseCase: FetchKitapByIsbnUseCase,
    private val saveKitapUseCase: SaveKitapUseCase,
    private val saveKitapKutuphaneUseCase: SaveKitapKutuphaneUseCase,
    private val sessionManager: SessionManager,
    private val findKutuphaneByAccountIdUseCase: FindKutuphaneByAccountIdUseCase
) : ViewModel() {

    val isbn = MutableLiveData<Long>()
    val kitapAdi = MutableLiveData<String>()
    val yayinTarihi = MutableLiveData<String>()
    val dil = MutableLiveData<String>()
    val sayfaSayisi = MutableLiveData<Long>()
    val aciklama = MutableLiveData<String>()
    var stok: Long? = null

    fun olustur(onResult: (MyResult<Unit>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            isbn.value?.let { isbn ->
                kitapAdi.value?.let { kitapAdi ->
                    yayinTarihi.value?.let { yayinTarihi ->
                        dil.value?.let { dil ->
                            sayfaSayisi.value?.let { sayfaSayisi ->
                                aciklama.value?.let { aciklama ->
                                    val saveKitapResult = async {
                                        saveKitapUseCase(
                                            KitapReq(
                                                isbn,
                                                kitapAdi,
                                                yayinTarihi,
                                                dil,
                                                sayfaSayisi.toInt(),
                                                aciklama
                                            )
                                        )
                                    }.await()
                                    when (saveKitapResult) {
                                        is MyResult.Success -> {
                                            val kitapId = saveKitapResult.data
                                            sessionManager.getAccountID()?.let {
                                                val kutuphaneResult = async {
                                                    findKutuphaneByAccountIdUseCase(it)
                                                }.await()
                                                when (kutuphaneResult) {
                                                    is MyResult.Success -> {
                                                        stok?.let { stok ->
                                                            val result = async {
                                                                saveKitapKutuphaneUseCase(
                                                                    KitapKutuphaneReq(
                                                                        kitapId,
                                                                        kutuphaneResult.data.id,
                                                                        stok.toInt()
                                                                    )
                                                                )
                                                            }.await()
                                                            withContext(Dispatchers.Main) {
                                                                onResult(result)
                                                            }
                                                        }
                                                    }

                                                    is MyResult.Error -> {
                                                        kutuphaneResult.exception.printStackTrace()
                                                    }
                                                }
                                            }
                                        }

                                        is MyResult.Error -> {
                                            saveKitapResult.exception.printStackTrace()
                                        }
                                    }
                                } ?: kotlin.run { println("Aciklama null") }
                            } ?: kotlin.run { println("Sayfa sayısı null") }
                        } ?: kotlin.run { println("Dil null") }
                    } ?: kotlin.run { println("Yayın tarihi null") }
                } ?: kotlin.run { println("Kitap adı null") }
            } ?: kotlin.run { println("Isbn null") }
        }
    }

    fun temizle() {
        println("View model temizle")
        kitapAdi.value = ""
        yayinTarihi.value = ""
        dil.value = ""
        sayfaSayisi.value = 0
        aciklama.value = ""
    }

    fun fetchKitapByIsbn(onResult: (MyResult<KitapReq>) -> Unit) {
        isbn.value?.let { isbn ->
            viewModelScope.launch(Dispatchers.IO) {
                val result = async { fetchKitapByIsbnUseCase(isbn) }.await()
                withContext(Dispatchers.Main) {
                    onResult(result)
                }
            }
        }
    }
}