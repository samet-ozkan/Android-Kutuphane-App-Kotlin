package com.sametozkan.kutuphane.domain.usecase.tur

import com.sametozkan.kutuphane.data.dto.response.TurRes
import com.sametozkan.kutuphane.domain.repository.TurRepository
import javax.inject.Inject
import com.sametozkan.kutuphane.util.MyResult


class FindAllTurUseCase @Inject constructor(private val turRepository: TurRepository) {

    suspend operator fun invoke(): MyResult<List<TurRes>> {
        return try {
            val response = turRepository.findAll()
            if (response.isSuccessful) {
                val turList = response.body()
                if (turList != null) {
                    MyResult.Success(turList)
                } else {
                    MyResult.Error(Exception("Tur list is null"))
                }
            } else {
                MyResult.Error(Exception("Failed to find all tur: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        }
    }
}