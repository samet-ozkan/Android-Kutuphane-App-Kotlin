package com.sametozkan.kutuphane.presentation.kullanici.home.kutuphaneler

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes
import com.sametozkan.kutuphane.domain.usecase.kutuphane.FindAllKutuphaneUseCase
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class KutuphanelerViewModel @Inject constructor(private val findAllKutuphaneUseCase : FindAllKutuphaneUseCase): ViewModel() {

    fun fetchKutuphaneler(onResult : (MyResult<List<KutuphaneRes>>) -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            val result = async { findAllKutuphaneUseCase() }.await()
            withContext(Dispatchers.Main){
                onResult(result)
            }
        }
    }
}