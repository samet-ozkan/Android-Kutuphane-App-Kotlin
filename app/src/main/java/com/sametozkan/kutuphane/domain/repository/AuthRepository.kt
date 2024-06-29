package com.sametozkan.kutuphane.domain.repository

import com.sametozkan.kutuphane.data.dto.request.KullaniciRegisterReq
import com.sametozkan.kutuphane.data.dto.request.KutuphaneRegisterReq
import com.sametozkan.kutuphane.data.dto.request.LoginReq
import com.sametozkan.kutuphane.data.dto.request.TokenRefreshReq
import com.sametozkan.kutuphane.data.dto.response.JwtRes
import com.sametozkan.kutuphane.data.dto.response.TokenRefreshRes
import retrofit2.Response

interface AuthRepository {

    suspend fun login(loginReq: LoginReq): Response<JwtRes>

    suspend fun registerKullanici(kullaniciRegisterReq: KullaniciRegisterReq): Response<Unit>

    suspend fun registerKutuphane(kutuphaneRegisterReq: KutuphaneRegisterReq): Response<Unit>
}