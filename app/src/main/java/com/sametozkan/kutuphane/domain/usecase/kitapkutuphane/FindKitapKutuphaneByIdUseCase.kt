package com.sametozkan.kutuphane.domain.usecase.kitapkutuphane

import com.sametozkan.kutuphane.data.dto.response.KitapKutuphaneRes
import com.sametozkan.kutuphane.domain.repository.KitapKutuphaneRepository
import com.sametozkan.kutuphane.domain.usecase.Result
import javax.inject.Inject

class FindKitapKutuphaneByIdUseCase @Inject constructor(private val kitapKutuphaneRepository: KitapKutuphaneRepository) {

    suspend operator fun invoke(id: Long): Result<KitapKutuphaneRes> {
        return try {
            val response = kitapKutuphaneRepository.findById(id)
            if (response.isSuccessful) {
                val kitapKutuphaneRes = response.body()
                if (kitapKutuphaneRes != null) {
                    Result.Success(kitapKutuphaneRes)
                } else {
                    Result.Error(Exception("Kitap kutuphane response body is null"))
                }
            } else {
                Result.Error(Exception("Failed to find kitap kutuphane by id: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}