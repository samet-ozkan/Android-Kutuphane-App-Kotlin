package com.sametozkan.kutuphane.domain.usecase.kutuphane

import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes
import com.sametozkan.kutuphane.domain.repository.KutuphaneRepository
import javax.inject.Inject
import com.sametozkan.kutuphane.domain.usecase.Result


class FindAllKutuphaneUseCase @Inject constructor(private val kutuphaneRepository: KutuphaneRepository) {

    suspend operator fun invoke(): Result<List<KutuphaneRes>> {
        return try {
            val response = kutuphaneRepository.findAll()
            if (response.isSuccessful) {
                val kutuphaneList = response.body()
                if (kutuphaneList != null) {
                    Result.Success(kutuphaneList)
                } else {
                    Result.Error(Exception("Kutuphane list is null"))
                }
            } else {
                Result.Error(Exception("Failed to find all kutuphane: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}