package com.sametozkan.kutuphane.presentation.kullanici.kutuphane.kitaplar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes
import com.sametozkan.kutuphane.domain.usecase.kutuphane.FindKutuphaneByIdUseCase
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class KitaplarViewModel @Inject constructor(private val findKutuphaneByIdUseCase: FindKutuphaneByIdUseCase): ViewModel() {

    var kutuphaneId: Long? = null
    val query = MutableLiveData<String>()
    var kitaplar = ArrayList<KitapRes>()

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

}