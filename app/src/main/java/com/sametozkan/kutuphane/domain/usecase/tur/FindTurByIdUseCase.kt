package com.sametozkan.kutuphane.domain.usecase.tur

import com.sametozkan.kutuphane.data.dto.response.TurRes
import com.sametozkan.kutuphane.domain.repository.TurRepository
import javax.inject.Inject
import com.sametozkan.kutuphane.domain.usecase.Result

class FindTurByIdUseCase @Inject constructor(private val turRepository: TurRepository) {

    suspend operator fun invoke(id: Long): Result<TurRes> {
        return try {
            val response = turRepository.findById(id)
            if (response.isSuccessful) {
                val turRes = response.body()
                if (turRes != null) {
                    Result.Success(turRes)
                } else {
                    Result.Error(Exception("Tur response body is null"))
                }
            } else {
                Result.Error(Exception("Failed to find tur by id: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}