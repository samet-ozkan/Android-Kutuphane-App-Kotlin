package com.sametozkan.kutuphane.presentation.kullanici.giris

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sametozkan.kutuphane.data.datasource.local.sharedpreferences.SessionManager
import com.sametozkan.kutuphane.data.dto.request.LoginReq
import com.sametozkan.kutuphane.data.dto.response.JwtRes
import com.sametozkan.kutuphane.domain.usecase.auth.LoginUseCase
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class KullaniciGirisViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun login(onResult: (MyResult<JwtRes>) -> Unit) {
        email.value?.let { email ->
            password.value?.let { password ->
                viewModelScope.launch(Dispatchers.IO) {
                    val result = async { loginUseCase(LoginReq(email, password)) }.await()
                    withContext(Dispatchers.Main) {
                        onResult(result)
                    }
                }
            }
        }
    }

    fun setSession(jwtRes: JwtRes) {
        sessionManager.apply {
            setJWT(jwtRes.jwt)
            setRefreshToken(jwtRes.refreshToken)
            setRefreshExpiryDate(jwtRes.refreshExpiryDate)
            setAccountID(jwtRes.accountId)
            setAccountType(jwtRes.accountType)
        }
    }
}