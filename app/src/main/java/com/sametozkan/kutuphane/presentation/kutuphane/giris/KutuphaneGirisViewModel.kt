package com.sametozkan.kutuphane.presentation.kutuphane.giris

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sametozkan.kutuphane.data.datasource.local.sharedpreferences.SessionManager
import com.sametozkan.kutuphane.data.dto.request.LoginReq
import com.sametozkan.kutuphane.data.dto.response.JwtRes
import com.sametozkan.kutuphane.domain.usecase.MyResult
import com.sametozkan.kutuphane.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KutuphaneGirisViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()

    fun login(onResult: (MyResult<JwtRes>) -> Unit) {
        val emailValue = email.value
        val passwordValue = password.value
        if (emailValue != null && passwordValue != null) {
            val loginReq = LoginReq(emailValue, passwordValue)
            viewModelScope.launch {
                onResult(loginUseCase(loginReq))
            }
        } else {
            println("Tüm alanları doldurun.")
        }
    }

    fun setSession(jwtRes: JwtRes) {
        sessionManager.apply {
            setJWT(jwtRes.jwt)
            setAccountID(jwtRes.accountId)
            setAccountType(jwtRes.accountType)
        }
    }


}