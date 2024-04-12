package com.sametozkan.kutuphane.domain.usecase.tur

import com.sametozkan.kutuphane.data.dto.response.TurRes
import com.sametozkan.kutuphane.domain.repository.TurRepository
import javax.inject.Inject
import com.sametozkan.kutuphane.domain.usecase.Result


class FindAllTurUseCase @Inject constructor(private val turRepository: TurRepository) {

    suspend operator fun invoke(): Result<List<TurRes>> {
        return try {
            val response = turRepository.findAll()
            if (response.isSuccessful) {
                val turList = response.body()
                if (turList != null) {
                    Result.Success(turList)
                } else {
                    Result.Error(Exception("Tur list is null"))
                }
            } else {
                Result.Error(Exception("Failed to find all tur: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}