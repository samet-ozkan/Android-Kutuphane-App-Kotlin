package com.sametozkan.kutuphane.data.datasource.remote.authenticator

import android.accounts.Account
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sametozkan.kutuphane.MainActivity
import com.sametozkan.kutuphane.data.datasource.local.sharedpreferences.SessionManager
import com.sametozkan.kutuphane.data.dto.request.TokenRefreshReq
import com.sametozkan.kutuphane.domain.usecase.auth.RefreshTokenUseCase
import com.sametozkan.kutuphane.presentation.kullanici.giris.KullaniciGirisActivity
import com.sametozkan.kutuphane.presentation.kutuphane.giris.KutuphaneGirisActivity
import com.sametozkan.kutuphane.util.AccountType
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.time.Instant
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    @ApplicationContext val context: Context,
    private val sessionManager: SessionManager,
    private val refreshTokenUseCase: RefreshTokenUseCase
) : Authenticator {
    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val TOKEN_TYPE = "Bearer"
        const val TAG = "AuthAuthenticator"
    }

    private fun showLoginScreen() {
        val accountType = sessionManager.getAccountType()
        sessionManager.clear()
        lateinit var intentClass: Class<out AppCompatActivity>
        accountType?.let {
            if (it.equals(AccountType.KUTUPHANE.type)) {
                intentClass = KutuphaneGirisActivity::class.java
            } else if (it.equals(AccountType.KULLANICI.type)) {
                intentClass = KullaniciGirisActivity::class.java
            } else {
                intentClass = MainActivity::class.java
            }

        } ?: kotlin.run {
            intentClass = MainActivity::class.java
        }
        val intent = Intent(context, intentClass)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == 401) {
            val currentToken = sessionManager.getJWT()

            val token = runBlocking {
                val updatedToken = sessionManager.getJWT()

                if (currentToken != updatedToken) {
                    updatedToken?.let {
                        response.request.newBuilder()
                            .header(HEADER_AUTHORIZATION, "$TOKEN_TYPE $it")
                            .build()
                    }
                } else {
                    sessionManager.getRefreshToken()?.let { refreshToken ->
                        val result =
                            async { refreshTokenUseCase(TokenRefreshReq(refreshToken)) }.await()
                        when (result) {
                            is MyResult.Success -> {
                                result.data?.let { newData ->
                                    runBlocking {
                                        sessionManager.setJWT(newData.jwt)
                                        sessionManager.setRefreshToken(newData.refreshToken)
                                        sessionManager.setRefreshExpiryDate(newData.refreshExpiryDate)
                                    }
                                    response.request.newBuilder()
                                        .header(HEADER_AUTHORIZATION, "$TOKEN_TYPE ${newData.jwt}")
                                        .build()
                                }
                            }

                            is MyResult.Error -> {
                                Log.d(TAG, "authenticate: error show login")
                                showLoginScreen()
                                null
                            }
                        }
                    }
                }
            }

            return token
        } else {
            return null
        }
    }

}