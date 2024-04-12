package com.sametozkan.kutuphane.domain.usecase.kitap

import com.sametozkan.kutuphane.data.dto.request.KitapReq
import com.sametozkan.kutuphane.domain.repository.KitapRepository
import com.sametozkan.kutuphane.domain.usecase.Result
import javax.inject.Inject

class SaveKitapUseCase @Inject constructor(private val kitapRepository: KitapRepository) {

    suspend operator fun invoke(kitapReq: KitapReq): Result<Unit> {
        return try {
            val response = kitapRepository.save(kitapReq)
            if (response.isSuccessful) {
                Result.Success(Unit)
            } else {
                Result.Error(Exception("Failed to save kitap: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}