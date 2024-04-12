package com.sametozkan.kutuphane.domain.usecase.kitaptur

import com.sametozkan.kutuphane.data.dto.request.KitapTurReq
import com.sametozkan.kutuphane.domain.repository.KitapTurRepository
import com.sametozkan.kutuphane.domain.usecase.Result
import javax.inject.Inject

class SaveKitapTurUseCase @Inject constructor(private val kitapTurRepository: KitapTurRepository) {

    suspend operator fun invoke(kitapTurReq: KitapTurReq): Result<Unit> {
        return try {
            val response = kitapTurRepository.save(kitapTurReq)
            if (response.isSuccessful) {
                Result.Success(Unit)
            } else {
                Result.Error(Exception("Failed to save kitap tur: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}