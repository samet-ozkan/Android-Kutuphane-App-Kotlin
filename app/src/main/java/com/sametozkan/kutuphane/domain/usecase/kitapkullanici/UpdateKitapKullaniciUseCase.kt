package com.sametozkan.kutuphane.domain.usecase.kitapkullanici

import com.sametozkan.kutuphane.data.dto.request.KitapKullaniciReq
import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import com.sametozkan.kutuphane.domain.repository.KitapKullaniciRepository
import com.sametozkan.kutuphane.domain.usecase.Result
import javax.inject.Inject

class UpdateKitapKullaniciUseCase @Inject constructor(private val kitapKullaniciRepository: KitapKullaniciRepository) {

    suspend operator fun invoke(id: Long, kitapKullaniciReq: KitapKullaniciReq): Result<KitapKullaniciRes> {
        return try {
            val response = kitapKullaniciRepository.update(id, kitapKullaniciReq)
            if (response.isSuccessful) {
                val kitapKullaniciRes = response.body()
                if (kitapKullaniciRes != null) {
                    Result.Success(kitapKullaniciRes)
                } else {
                    Result.Error(Exception("Kitap kullanici response body is null"))
                }
            } else {
                Result.Error(Exception("Failed to update kitap kullanici: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}