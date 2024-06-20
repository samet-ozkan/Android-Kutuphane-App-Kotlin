package com.sametozkan.kutuphane.data.datasource.remote

import com.sametozkan.kutuphane.data.dto.request.KullaniciRegisterReq
import com.sametozkan.kutuphane.data.dto.request.LoginReq
import com.sametozkan.kutuphane.data.dto.request.TokenRefreshReq
import com.sametozkan.kutuphane.data.dto.response.JwtRes
import com.sametozkan.kutuphane.data.dto.response.TokenRefreshRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {

    @POST("/api/auth/login")
    suspend fun login(@Body loginReq: LoginReq): Response<JwtRes>

    @POST("/api/auth/register/kullanici")
    suspend fun registerKullanici(@Body kullaniciRegisterReq: KullaniciRegisterReq): Response<Unit>

}