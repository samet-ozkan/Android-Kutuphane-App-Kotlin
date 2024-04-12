package com.sametozkan.kutuphane.domain.usecase.auth

import com.sametozkan.kutuphane.data.dto.request.KullaniciRegisterReq
import com.sametozkan.kutuphane.domain.repository.AuthRepository
import com.sametozkan.kutuphane.domain.usecase.Result
import javax.inject.Inject

class RegisterKullaniciUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(kullaniciRegisterReq: KullaniciRegisterReq): Result<Unit> {
        return try {
            val response = authRepository.registerKullanici(kullaniciRegisterReq)
            if (response.isSuccessful) {
                Result.Success(Unit)
            } else {
                Result.Error(Exception("Failed to register kullanici: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}