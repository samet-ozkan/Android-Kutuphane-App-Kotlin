package com.sametozkan.kutuphane.domain.usecase.yazar

import com.sametozkan.kutuphane.data.dto.request.YazarReq
import com.sametozkan.kutuphane.domain.repository.YazarRepository
import javax.inject.Inject
import com.sametozkan.kutuphane.domain.usecase.Result


class SaveYazarUseCase @Inject constructor(private val yazarRepository: YazarRepository) {

    suspend operator fun invoke(yazarReq: YazarReq): Result<Unit> {
        return try {
            val response = yazarRepository.save(yazarReq)
            if (response.isSuccessful) {
                Result.Success(Unit)
            } else {
                Result.Error(Exception("Failed to save yazar: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}