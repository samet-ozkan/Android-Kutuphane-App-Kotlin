package com.sametozkan.kutuphane.domain.usecase.kitap

import com.sametozkan.kutuphane.data.dto.request.KitapReq
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.domain.repository.KitapRepository
import com.sametozkan.kutuphane.domain.usecase.Result
import javax.inject.Inject

class UpdateKitapUseCase @Inject constructor(private val kitapRepository: KitapRepository) {

    suspend operator fun invoke(id: Long, kitapReq: KitapReq): Result<KitapRes> {
        return try {
            val response = kitapRepository.update(id, kitapReq)
            if (response.isSuccessful) {
                val kitapRes = response.body()
                if (kitapRes != null) {
                    Result.Success(kitapRes)
                } else {
                    Result.Error(Exception("Kitap response body is null"))
                }
            } else {
                Result.Error(Exception("Failed to update kitap: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}