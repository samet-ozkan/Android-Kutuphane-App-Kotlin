package com.sametozkan.kutuphane.domain.usecase.kutuphane

import com.sametozkan.kutuphane.data.dto.request.KutuphaneReq
import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes
import com.sametozkan.kutuphane.domain.repository.KutuphaneRepository
import javax.inject.Inject
import com.sametozkan.kutuphane.util.MyResult

class UpdateKutuphaneUseCase @Inject constructor(private val kutuphaneRepository: KutuphaneRepository) {

    suspend operator fun invoke(id: Long, kutuphaneReq: KutuphaneReq): MyResult<KutuphaneRes> {
        return try {
            val response = kutuphaneRepository.update(id, kutuphaneReq)
            if (response.isSuccessful) {
                val kutuphaneRes = response.body()
                if (kutuphaneRes != null) {
                    MyResult.Success(kutuphaneRes)
                } else {
                    MyResult.Error(Exception("Kutuphane response body is null"))
                }
            } else {
                MyResult.Error(Exception("Failed to update kutuphane: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        }
    }
}