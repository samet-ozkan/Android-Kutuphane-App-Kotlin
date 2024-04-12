package com.sametozkan.kutuphane.domain.usecase.tur

import com.sametozkan.kutuphane.data.dto.request.TurReq
import com.sametozkan.kutuphane.data.dto.response.TurRes
import com.sametozkan.kutuphane.domain.repository.TurRepository
import javax.inject.Inject
import com.sametozkan.kutuphane.domain.usecase.Result

class UpdateTurUseCase @Inject constructor(private val turRepository: TurRepository) {

    suspend operator fun invoke(id: Long, turReq: TurReq): Result<TurRes> {
        return try {
            val response = turRepository.update(id, turReq)
            if (response.isSuccessful) {
                val turRes = response.body()
                if (turRes != null) {
                    Result.Success(turRes)
                } else {
                    Result.Error(Exception("Tur response body is null"))
                }
            } else {
                Result.Error(Exception("Failed to update tur: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}