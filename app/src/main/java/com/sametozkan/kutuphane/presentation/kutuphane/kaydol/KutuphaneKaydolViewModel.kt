package com.sametozkan.kutuphane.presentation.kutuphane.kaydol

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sametozkan.kutuphane.data.dto.request.AccountReq
import com.sametozkan.kutuphane.data.dto.request.KutuphaneRegisterReq
import com.sametozkan.kutuphane.data.dto.request.KutuphaneReq
import com.sametozkan.kutuphane.domain.usecase.auth.RegisterKutuphaneUseCase
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
class KutuphaneKaydolViewModel @Inject constructor(private val registerKutuphaneUseCase: RegisterKutuphaneUseCase) :
    ViewModel() {

    val ad = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordTekrar = MutableLiveData<String>()
    val telefon = MutableLiveData<String>()
    val adres = MutableLiveData<String>()
    val sehir = MutableLiveData<String>()
    val teslimSuresi = MutableLiveData<String>()
    val dogrulamaKodu = MutableLiveData<String>()

    fun kaydol(onResult: (MyResult<Unit>) -> Unit) {
        if (ad.value.isNullOrEmpty() ||
            email.value.isNullOrEmpty() ||
            password.value.isNullOrEmpty() ||
            passwordTekrar.value.isNullOrEmpty() ||
            telefon.value.isNullOrEmpty() ||
            adres.value.isNullOrEmpty() ||
            sehir.value.isNullOrEmpty() ||
            teslimSuresi.value.isNullOrEmpty() ||
            dogrulamaKodu.value.isNullOrEmpty()
        ) {
            onResult(
                MyResult.Error(
                    MyException.AllFieldsMustBeFilledException("Tüm alanları doldurun."),
                    null
                )
            )
        } else {
            if (password.value != passwordTekrar.value)
                onResult(
                    MyResult.Error(
                        MyException.PasswordMismatchException("Şifreler uyuşmuyor."),
                        null
                    )
                )
            else
                viewModelScope.launch(Dispatchers.IO) {
                    val result = async {
                        registerKutuphaneUseCase(
                            KutuphaneRegisterReq(
                                AccountReq(
                                    email.value!!, password.value!!, AccountType.KUTUPHANE.type
                                ),
                                KutuphaneReq(
                                    ad.value!!,
                                    adres.value!!,
                                    sehir.value!!,
                                    teslimSuresi.value!!.toInt(),
                                    telefon.value!!
                                ),
                                dogrulamaKodu.value!!
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