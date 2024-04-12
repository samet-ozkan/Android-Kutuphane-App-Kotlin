package com.sametozkan.kutuphane.data.repository

import com.sametozkan.kutuphane.data.datasource.remote.AuthService
import com.sametozkan.kutuphane.data.dto.request.KullaniciRegisterReq
import com.sametozkan.kutuphane.data.dto.request.LoginReq
import com.sametozkan.kutuphane.data.dto.response.JwtRes
import com.sametozkan.kutuphane.domain.repository.AuthRepository
import retrofit2.Response

class AuthRepositoryImpl(private val authService: AuthService) : AuthRepository {

    override suspend fun login(loginReq: LoginReq): Response<JwtRes> {
        return authService.login(loginReq)
    }

    override suspend fun registerKullanici(kullaniciRegisterReq: KullaniciRegisterReq): Response<Unit> {
        return authService.registerKullanici(kullaniciRegisterReq)
    }
}