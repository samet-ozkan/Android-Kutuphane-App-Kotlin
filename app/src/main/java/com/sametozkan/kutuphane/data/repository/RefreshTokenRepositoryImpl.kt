package com.sametozkan.kutuphane.data.repository

import com.sametozkan.kutuphane.data.datasource.remote.RefreshTokenService
import com.sametozkan.kutuphane.data.dto.request.TokenRefreshReq
import com.sametozkan.kutuphane.data.dto.response.TokenRefreshRes
import com.sametozkan.kutuphane.domain.repository.RefreshTokenRepository
import retrofit2.Response

class RefreshTokenRepositoryImpl(private val refreshTokenService: RefreshTokenService) :
    RefreshTokenRepository {

    override suspend fun refreshToken(tokenRefreshReq: TokenRefreshReq): Response<TokenRefreshRes> {
        return refreshTokenService.refreshToken(tokenRefreshReq)
    }
}