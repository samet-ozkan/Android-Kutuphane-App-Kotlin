package com.sametozkan.kutuphane.data.datasource.remote

import com.sametozkan.kutuphane.data.dto.request.TokenRefreshReq
import com.sametozkan.kutuphane.data.dto.response.TokenRefreshRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RefreshTokenService {

    @POST("/api/auth/refresh-token")
    suspend fun refreshToken(@Body tokenRefreshReq: TokenRefreshReq): Response<TokenRefreshRes>
}