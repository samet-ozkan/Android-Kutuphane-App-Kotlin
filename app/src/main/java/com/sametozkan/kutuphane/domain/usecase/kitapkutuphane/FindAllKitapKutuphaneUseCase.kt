package com.sametozkan.kutuphane.domain.usecase.kitapkutuphane

import com.sametozkan.kutuphane.data.dto.response.KitapKutuphaneRes
import com.sametozkan.kutuphane.domain.repository.KitapKutuphaneRepository
import com.sametozkan.kutuphane.domain.usecase.Result
import javax.inject.Inject

class FindAllKitapKutuphaneUseCase @Inject constructor(private val kitapKutuphaneRepository: KitapKutuphaneRepository) {

    suspend operator fun invoke(): Result<List<KitapKutuphaneRes>> {
        return try {
            val response = kitapKutuphaneRepository.findAll()
            if (response.isSuccessful) {
                val kitapKutuphaneList = response.body()
                if (kitapKutuphaneList != null) {
                    Result.Success(kitapKutuphaneList)
                } else {
                    Result.Error(Exception("Kitap kutuphane list is null"))
                }
            } else {
                Result.Error(Exception("Failed to find all kitap kutuphane: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}