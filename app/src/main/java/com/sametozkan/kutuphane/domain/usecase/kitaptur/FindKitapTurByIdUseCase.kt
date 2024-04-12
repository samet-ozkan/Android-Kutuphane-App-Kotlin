package com.sametozkan.kutuphane.domain.usecase.kitaptur

import com.sametozkan.kutuphane.data.dto.response.KitapTurRes
import com.sametozkan.kutuphane.domain.repository.KitapTurRepository
import com.sametozkan.kutuphane.domain.usecase.Result
import javax.inject.Inject

class FindKitapTurByIdUseCase @Inject constructor(private val kitapTurRepository: KitapTurRepository) {

    suspend operator fun invoke(id: Long): Result<KitapTurRes> {
        return try {
            val response = kitapTurRepository.findById(id)
            if (response.isSuccessful) {
                val kitapTurRes = response.body()
                if (kitapTurRes != null) {
                    Result.Success(kitapTurRes)
                } else {
                    Result.Error(Exception("Kitap tur response body is null"))
                }
            } else {
                Result.Error(Exception("Failed to find kitap tur by id: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}