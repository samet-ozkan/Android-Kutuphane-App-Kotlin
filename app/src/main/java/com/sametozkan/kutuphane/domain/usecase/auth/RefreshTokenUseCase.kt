package com.sametozkan.kutuphane.domain.usecase.auth

import android.util.Log
import com.sametozkan.kutuphane.data.dto.request.TokenRefreshReq
import com.sametozkan.kutuphane.data.dto.response.TokenRefreshRes
import com.sametozkan.kutuphane.domain.repository.AuthRepository
import com.sametozkan.kutuphane.domain.repository.RefreshTokenRepository
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(private val refreshTokenRepository: RefreshTokenRepository) {

    companion object{
        const val TAG = "RefreshTokenUseCase"
    }

    suspend operator fun invoke(tokenRefreshReq: TokenRefreshReq): MyResult<TokenRefreshRes> {
        Log.d(TAG, "invoke: req " + tokenRefreshReq.refreshToken)
        return try {
            val response = refreshTokenRepository.refreshToken(tokenRefreshReq)
            Log.d(TAG, "invoke: response " + response.code())
            if (response.isSuccessful) {
                val tokenRefreshRes = response.body()
                if (tokenRefreshRes != null) {
                    return MyResult.Success(tokenRefreshRes)
                } else {
                    MyResult.Error(Exception("Token refresh body is null"))
                }
            } else {
                MyResult.Error(Exception("Failed to refreshing token: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        };
    }
    }