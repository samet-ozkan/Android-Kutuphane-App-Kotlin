package com.sametozkan.kutuphane.domain.repository

import com.sametozkan.kutuphane.data.dto.request.KullaniciRegisterReq
import com.sametozkan.kutuphane.data.dto.request.LoginReq
import com.sametozkan.kutuphane.data.dto.response.JwtRes
import retrofit2.Response

interface AuthRepository {

    suspend fun login(loginReq: LoginReq): Response<JwtRes>

    suspend fun registerKullanici(kullaniciRegisterReq: KullaniciRegisterReq): Response<Unit>
}