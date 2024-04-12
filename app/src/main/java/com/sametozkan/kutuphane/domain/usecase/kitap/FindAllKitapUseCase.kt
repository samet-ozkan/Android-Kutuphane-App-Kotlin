package com.sametozkan.kutuphane.domain.usecase.kitap

import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.domain.repository.KitapRepository
import com.sametozkan.kutuphane.domain.usecase.Result
import javax.inject.Inject

class FindAllKitapUseCase @Inject constructor(private val kitapRepository: KitapRepository) {

    suspend operator fun invoke(): Result<List<KitapRes>> {
        return try {
            val response = kitapRepository.findAll()
            if (response.isSuccessful) {
                val kitapList = response.body()
                if (kitapList != null) {
                    Result.Success(kitapList)
                } else {
                    Result.Error(Exception("Kitap list is null"))
                }
            } else {
                Result.Error(Exception("Failed to find all kitap: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}