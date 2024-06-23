package com.sametozkan.kutuphane.domain.usecase.auth

import com.sametozkan.kutuphane.data.dto.request.KullaniciRegisterReq
import com.sametozkan.kutuphane.domain.repository.AuthRepository
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class RegisterKullaniciUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(kullaniciRegisterReq: KullaniciRegisterReq): MyResult<Unit> {
        LoadingManager.startLoading()
        return try {
            val response = authRepository.registerKullanici(kullaniciRegisterReq)
            if (response.isSuccessful) {
                MyResult.Success(Unit)
            } else {
                MyResult.Error(Exception("Failed to register kullanici: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}