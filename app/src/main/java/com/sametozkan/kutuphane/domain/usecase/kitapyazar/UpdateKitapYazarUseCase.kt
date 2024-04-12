package com.sametozkan.kutuphane.domain.usecase.kitapyazar

import com.sametozkan.kutuphane.data.dto.request.KitapYazarReq
import com.sametozkan.kutuphane.data.dto.response.KitapYazarRes
import com.sametozkan.kutuphane.domain.repository.KitapYazarRepository
import com.sametozkan.kutuphane.domain.usecase.Result
import javax.inject.Inject

class UpdateKitapYazarUseCase @Inject constructor(private val kitapYazarRepository: KitapYazarRepository) {

    suspend operator fun invoke(id: Long, kitapYazarReq: KitapYazarReq): Result<KitapYazarRes> {
        return try {
            val response = kitapYazarRepository.update(id, kitapYazarReq)
            if (response.isSuccessful) {
                val kitapYazarRes = response.body()
                if (kitapYazarRes != null) {
                    Result.Success(kitapYazarRes)
                } else {
                    Result.Error(Exception("Kitap yazar response body is null"))
                }
            } else {
                Result.Error(Exception("Failed to update kitap yazar: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}