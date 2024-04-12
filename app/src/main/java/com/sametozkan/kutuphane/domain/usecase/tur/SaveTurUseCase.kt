package com.sametozkan.kutuphane.domain.usecase.tur

import com.sametozkan.kutuphane.data.dto.request.TurReq
import com.sametozkan.kutuphane.domain.repository.TurRepository
import javax.inject.Inject
import com.sametozkan.kutuphane.domain.usecase.Result


class SaveTurUseCase @Inject constructor(private val turRepository: TurRepository) {

    suspend operator fun invoke(turReq: TurReq): Result<Unit> {
        return try {
            val response = turRepository.save(turReq)
            if (response.isSuccessful) {
                Result.Success(Unit)
            } else {
                Result.Error(Exception("Failed to save tur: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}