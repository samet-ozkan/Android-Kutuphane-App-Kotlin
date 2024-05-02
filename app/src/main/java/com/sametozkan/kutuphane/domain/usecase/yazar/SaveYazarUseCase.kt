package com.sametozkan.kutuphane.domain.usecase.yazar

import com.sametozkan.kutuphane.data.dto.request.YazarReq
import com.sametozkan.kutuphane.domain.repository.YazarRepository
import javax.inject.Inject
import com.sametozkan.kutuphane.util.MyResult


class SaveYazarUseCase @Inject constructor(private val yazarRepository: YazarRepository) {

    suspend operator fun invoke(yazarReq: YazarReq): MyResult<Unit> {
        return try {
            val response = yazarRepository.save(yazarReq)
            if (response.isSuccessful) {
                MyResult.Success(Unit)
            } else {
                MyResult.Error(Exception("Failed to save yazar: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        }
    }
}