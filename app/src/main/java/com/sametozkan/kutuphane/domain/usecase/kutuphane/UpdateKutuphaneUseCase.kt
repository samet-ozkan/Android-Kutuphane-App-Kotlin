package com.sametozkan.kutuphane.domain.usecase.kutuphane

import com.sametozkan.kutuphane.data.dto.request.KutuphaneReq
import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes
import com.sametozkan.kutuphane.domain.repository.KutuphaneRepository
import javax.inject.Inject
import com.sametozkan.kutuphane.domain.usecase.Result

class UpdateKutuphaneUseCase @Inject constructor(private val kutuphaneRepository: KutuphaneRepository) {

    suspend operator fun invoke(id: Long, kutuphaneReq: KutuphaneReq): Result<KutuphaneRes> {
        return try {
            val response = kutuphaneRepository.update(id, kutuphaneReq)
            if (response.isSuccessful) {
                val kutuphaneRes = response.body()
                if (kutuphaneRes != null) {
                    Result.Success(kutuphaneRes)
                } else {
                    Result.Error(Exception("Kutuphane response body is null"))
                }
            } else {
                Result.Error(Exception("Failed to update kutuphane: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}