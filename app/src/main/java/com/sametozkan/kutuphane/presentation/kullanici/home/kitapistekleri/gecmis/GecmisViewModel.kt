package com.sametozkan.kutuphane.presentation.kullanici.home.kitapistekleri.gecmis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sametozkan.kutuphane.data.datasource.local.sharedpreferences.SessionManager
import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import com.sametozkan.kutuphane.domain.usecase.kitapkullanici.FindByKullaniciIdAndIadeDurumuIsTrueUseCase
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GecmisViewModel @Inject constructor(
    private val findByKullaniciIdAndIadeDurumuIsTrueUseCase: FindByKullaniciIdAndIadeDurumuIsTrueUseCase,
    private val sessionManager: SessionManager,
) : ViewModel() {

    fun fetchMevcut(onResult: (MyResult<List<KitapKullaniciRes>>) -> Unit){
        sessionManager.getAccountID()?.let { account_id ->
            viewModelScope.launch(Dispatchers.IO) {
                val result =
                    async { findByKullaniciIdAndIadeDurumuIsTrueUseCase(account_id) }.await()
                withContext(Dispatchers.Main) {
                    onResult(result)
                }
            }
        }
    }
}