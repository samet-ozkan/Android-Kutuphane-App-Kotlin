package com.sametozkan.kutuphane.domain.usecase.yazar

import com.sametozkan.kutuphane.data.dto.response.YazarRes
import com.sametozkan.kutuphane.domain.repository.YazarRepository
import javax.inject.Inject
import com.sametozkan.kutuphane.domain.usecase.Result

class FindAllYazarUseCase @Inject constructor(private val yazarRepository: YazarRepository) {

    suspend operator fun invoke(): Result<List<YazarRes>> {
        return try {
            val response = yazarRepository.findAll()
            if (response.isSuccessful) {
                val yazarList = response.body()
                if (yazarList != null) {
                    Result.Success(yazarList)
                } else {
                    Result.Error(Exception("Yazar list is null"))
                }
            } else {
                Result.Error(Exception("Failed to find all yazar: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}