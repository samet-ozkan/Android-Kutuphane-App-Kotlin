package com.sametozkan.kutuphane.domain.usecase.kutuphane

import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes
import com.sametozkan.kutuphane.domain.repository.KutuphaneRepository
import javax.inject.Inject
import com.sametozkan.kutuphane.domain.usecase.Result


class FindKutuphaneByIdUseCase @Inject constructor(private val kutuphaneRepository: KutuphaneRepository) {

    suspend operator fun invoke(id: Long): Result<KutuphaneRes> {
        return try {
            val response = kutuphaneRepository.findById(id)
            if (response.isSuccessful) {
                val kutuphaneRes = response.body()
                if (kutuphaneRes != null) {
                    Result.Success(kutuphaneRes)
                } else {
                    Result.Error(Exception("Kutuphane response body is null"))
                }
            } else {
                Result.Error(Exception("Failed to find kutuphane by id: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}