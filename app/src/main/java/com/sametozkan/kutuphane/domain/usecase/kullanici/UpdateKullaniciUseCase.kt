package com.sametozkan.kutuphane.domain.usecase.kullanici

import com.sametozkan.kutuphane.data.dto.request.KullaniciReq
import com.sametozkan.kutuphane.data.dto.response.KullaniciRes
import com.sametozkan.kutuphane.domain.repository.KullaniciRepository
import javax.inject.Inject
import com.sametozkan.kutuphane.domain.usecase.Result

class UpdateKullaniciUseCase @Inject constructor(private val kullaniciRepository: KullaniciRepository) {

    suspend operator fun invoke(id: Long, kullaniciReq: KullaniciReq): Result<KullaniciRes> {
        return try {
            val response = kullaniciRepository.update(id, kullaniciReq)
            if (response.isSuccessful) {
                val kullaniciRes = response.body()
                if (kullaniciRes != null) {
                    Result.Success(kullaniciRes)
                } else {
                    Result.Error(Exception("Kullanici response body is null"))
                }
            } else {
                Result.Error(Exception("Failed to update kullanici: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}