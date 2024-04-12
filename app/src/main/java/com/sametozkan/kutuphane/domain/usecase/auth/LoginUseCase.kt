package com.sametozkan.kutuphane.domain.usecase.auth

import com.sametozkan.kutuphane.data.dto.request.LoginReq
import com.sametozkan.kutuphane.data.dto.response.JwtRes
import com.sametozkan.kutuphane.domain.repository.AuthRepository
import com.sametozkan.kutuphane.domain.usecase.Result
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(loginReq: LoginReq): Result<JwtRes> {
        return try {
            val response = authRepository.login(loginReq)
            if (response.isSuccessful) {
                val jwtRes = response.body()
                if (jwtRes != null) {
                    Result.Success(jwtRes)
                } else {
                    Result.Error(Exception("JWT response body is null"))
                }
            } else {
                Result.Error(Exception("Failed to login: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}