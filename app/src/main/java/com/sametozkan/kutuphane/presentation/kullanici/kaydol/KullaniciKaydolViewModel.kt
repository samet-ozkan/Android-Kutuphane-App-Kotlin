package com.sametozkan.kutuphane.presentation.kullanici.kaydol

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sametozkan.kutuphane.data.dto.request.AccountReq
import com.sametozkan.kutuphane.data.dto.request.KullaniciRegisterReq
import com.sametozkan.kutuphane.data.dto.request.KullaniciReq
import com.sametozkan.kutuphane.domain.usecase.auth.RegisterKullaniciUseCase
import com.sametozkan.kutuphane.util.AccountType
import com.sametozkan.kutuphane.util.MyException
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class KullaniciKaydolViewModel @Inject constructor(private val registerKullaniciUseCase: RegisterKullaniciUseCase) :
    ViewModel() {

    val ad = MutableLiveData<String>()
    val soyad = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordTekrar = MutableLiveData<String>()
    val telefon = MutableLiveData<String>()
    val adres = MutableLiveData<String>()
    val dogumTarihi = MutableLiveData<String>()

    fun kaydol(onResult: (MyResult<Unit>) -> Unit) {
        if (ad.value.isNullOrEmpty() ||
            soyad.value.isNullOrEmpty() ||
            email.value.isNullOrEmpty() ||
            password.value.isNullOrEmpty() ||
            passwordTekrar.value.isNullOrEmpty() ||
            telefon.value.isNullOrEmpty() ||
            adres.value.isNullOrEmpty() ||
            dogumTarihi.value.isNullOrEmpty()
        ) {
            onResult(
                MyResult.Error(
                    MyException.AllFieldsMustBeFilledException("Tüm alanları doldurun."),
                    null
                )
            )
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                val result = async {
                    registerKullaniciUseCase(
                        KullaniciRegisterReq(
                            AccountReq(
                                email.value!!,
                                password.value!!,
                                AccountType.KULLANICI.type
                            ),
                            KullaniciReq(
                                ad.value!!,
                                soyad.value!!,
                                telefon.value!!,
                                adres.value!!,
                                dogumTarihi.value!!
                            )
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