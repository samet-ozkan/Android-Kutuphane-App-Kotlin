package com.sametozkan.kutuphane.domain.usecase.auth

import com.sametozkan.kutuphane.data.dto.request.KullaniciRegisterReq
import com.sametozkan.kutuphane.data.dto.request.KutuphaneRegisterReq
import com.sametozkan.kutuphane.domain.repository.AuthRepository
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyException
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class RegisterKutuphaneUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(kutuphaneRegisterReq: KutuphaneRegisterReq): MyResult<Unit> {
        LoadingManager.startLoading()
        return try {
            val response = authRepository.registerKutuphane(kutuphaneRegisterReq)
            if (response.isSuccessful) {
                MyResult.Success(Unit)
            } else {
                if (response.code() == 403)
                    MyResult.Error(MyException.InvalidVerificationCodeException("Geçersiz doğrulama kodu hatası!"), response.code())
                MyResult.Error(Exception("Failed to register kullanici!"), response.code())
            }
        } catch (e: Exception) {
            MyResult.Error(e, null)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}