package com.sametozkan.kutuphane.domain.usecase.kitapkutuphane

import com.sametozkan.kutuphane.data.dto.request.KitapKutuphaneReq
import com.sametozkan.kutuphane.domain.repository.KitapKutuphaneRepository
import com.sametozkan.kutuphane.domain.usecase.Result
import javax.inject.Inject

class SaveKitapKutuphaneUseCase @Inject constructor(private val kitapKutuphaneRepository: KitapKutuphaneRepository) {

    suspend operator fun invoke(kitapKutuphaneReq: KitapKutuphaneReq): Result<Unit> {
        return try {
            val response = kitapKutuphaneRepository.save(kitapKutuphaneReq)
            if (response.isSuccessful) {
                Result.Success(Unit)
            } else {
                Result.Error(Exception("Failed to save kitap kutuphane: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}