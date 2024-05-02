package com.sametozkan.kutuphane.domain.usecase.kutuphane

import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes
import com.sametozkan.kutuphane.domain.repository.KutuphaneRepository
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class FindKutuphaneByAccountIdUseCase @Inject constructor(private val kutuphaneRepository: KutuphaneRepository) {

    suspend operator fun invoke(accountId: Long): MyResult<KutuphaneRes> {
        return try {
            val response = kutuphaneRepository.findByAccountId(accountId)
            if (response.isSuccessful) {
                val kutuphaneRes = response.body()
                if (kutuphaneRes != null) {
                    MyResult.Success(kutuphaneRes)
                } else {
                    MyResult.Error(Exception("Kutuphane response body is null"))
                }
            } else {
                MyResult.Error(Exception("Failed to find kutuphane by id: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        }
    }
}