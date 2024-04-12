package com.sametozkan.kutuphane.domain.usecase.yazar

import com.sametozkan.kutuphane.data.dto.response.YazarRes
import com.sametozkan.kutuphane.domain.repository.YazarRepository
import javax.inject.Inject
import com.sametozkan.kutuphane.domain.usecase.Result

class FindYazarByIdUseCase @Inject constructor(private val yazarRepository: YazarRepository) {

    suspend operator fun invoke(id: Long): Result<YazarRes> {
        return try {
            val response = yazarRepository.findById(id)
            if (response.isSuccessful) {
                val yazarRes = response.body()
                if (yazarRes != null) {
                    Result.Success(yazarRes)
                } else {
                    Result.Error(Exception("Yazar response body is null"))
                }
            } else {
                Result.Error(Exception("Failed to find yazar by id: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}