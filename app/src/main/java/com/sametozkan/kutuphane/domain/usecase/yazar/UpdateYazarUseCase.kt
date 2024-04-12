package com.sametozkan.kutuphane.domain.usecase.yazar

import com.sametozkan.kutuphane.data.dto.request.YazarReq
import com.sametozkan.kutuphane.data.dto.response.YazarRes
import com.sametozkan.kutuphane.domain.repository.YazarRepository
import javax.inject.Inject
import com.sametozkan.kutuphane.domain.usecase.Result

class UpdateYazarUseCase @Inject constructor(private val yazarRepository: YazarRepository) {

    suspend operator fun invoke(id: Long, yazarReq: YazarReq): Result<YazarRes> {
        return try {
            val response = yazarRepository.update(id, yazarReq)
            if (response.isSuccessful) {
                val yazarRes = response.body()
                if (yazarRes != null) {
                    Result.Success(yazarRes)
                } else {
                    Result.Error(Exception("Yazar response body is null"))
                }
            } else {
                Result.Error(Exception("Failed to update yazar: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}