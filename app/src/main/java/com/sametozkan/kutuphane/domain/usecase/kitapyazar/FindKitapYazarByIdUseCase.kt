package com.sametozkan.kutuphane.domain.usecase.kitapyazar

import com.sametozkan.kutuphane.data.dto.response.KitapYazarRes
import com.sametozkan.kutuphane.domain.repository.KitapYazarRepository
import javax.inject.Inject
import com.sametozkan.kutuphane.domain.usecase.Result


class FindKitapYazarByIdUseCase @Inject constructor(private val kitapYazarRepository: KitapYazarRepository) {

    suspend operator fun invoke(id: Long): Result<KitapYazarRes> {
        return try {
            val response = kitapYazarRepository.findById(id)
            if (response.isSuccessful) {
                val kitapYazarRes = response.body()
                if (kitapYazarRes != null) {
                    Result.Success(kitapYazarRes)
                } else {
                    Result.Error(Exception("Kitap yazar response body is null"))
                }
            } else {
                Result.Error(Exception("Failed to find kitap yazar by id: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}