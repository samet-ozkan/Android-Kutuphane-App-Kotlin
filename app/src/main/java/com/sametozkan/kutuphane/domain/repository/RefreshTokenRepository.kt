package com.sametozkan.kutuphane.domain.repository

import com.sametozkan.kutuphane.data.dto.request.TokenRefreshReq
import com.sametozkan.kutuphane.data.dto.response.TokenRefreshRes
import retrofit2.Response

interface RefreshTokenRepository {

    suspend fun refreshToken(tokenRefreshReq: TokenRefreshReq): Response<TokenRefreshRes>

}