package com.sametozkan.kutuphane.presentation.kullanici.home.kitapistekleri.onaybekleyenler

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sametozkan.kutuphane.data.datasource.local.sharedpreferences.SessionManager
import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import com.sametozkan.kutuphane.domain.usecase.kitapkullanici.DeleteKitapKullaniciByIdUseCase
import com.sametozkan.kutuphane.domain.usecase.kitapkullanici.FindByKullaniciIdAndOnaylandiIsNullUseCase
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OnayBekleyenlerViewModel @Inject constructor(
    private val findByKullaniciIdAndOnaylandiIsNullUseCase: FindByKullaniciIdAndOnaylandiIsNullUseCase,
    private val sessionManager: SessionManager,
    private val deleteKitapKullaniciByIdUseCase: DeleteKitapKullaniciByIdUseCase
) : ViewModel() {

    fun fetchOnayBekleyenler(onResult: (MyResult<List<KitapKullaniciRes>>) -> Unit){
        sessionManager.getAccountID()?.let { account_id ->
            viewModelScope.launch(Dispatchers.IO) {
                val result =
                    async { findByKullaniciIdAndOnaylandiIsNullUseCase(account_id) }.await()
                withContext(Dispatchers.Main) {
                    onResult(result)
                }
            }
        }
    }

    fun deleteById(id: Long, onResult: (MyResult<Unit>) -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            val result = async { deleteKitapKullaniciByIdUseCase(id) }.await()
            withContext(Dispatchers.Main){
                onResult(result)
            }
        }
    }
}