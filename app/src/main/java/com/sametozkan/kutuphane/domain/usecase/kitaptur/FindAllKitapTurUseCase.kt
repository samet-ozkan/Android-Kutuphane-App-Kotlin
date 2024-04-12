package com.sametozkan.kutuphane.domain.usecase.kitaptur

import com.sametozkan.kutuphane.data.dto.response.KitapTurRes
import com.sametozkan.kutuphane.domain.repository.KitapTurRepository
import com.sametozkan.kutuphane.domain.usecase.Result
import javax.inject.Inject

class FindAllKitapTurUseCase @Inject constructor(private val kitapTurRepository: KitapTurRepository) {

    suspend operator fun invoke(): Result<List<KitapTurRes>> {
        return try {
            val response = kitapTurRepository.findAll()
            if (response.isSuccessful) {
                val kitapTurList = response.body()
                if (kitapTurList != null) {
                    Result.Success(kitapTurList)
                } else {
                    Result.Error(Exception("Kitap tur list is null"))
                }
            } else {
                Result.Error(Exception("Failed to find all kitap tur: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}