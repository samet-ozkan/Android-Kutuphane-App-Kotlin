package com.sametozkan.kutuphane.domain.usecase.kutuphaneyorum

import com.sametozkan.kutuphane.data.dto.response.KutuphaneYorumRes
import com.sametozkan.kutuphane.domain.repository.KutuphaneYorumRepository
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class FindYorumlarByKutuphaneIdUseCase @Inject constructor(private val kutuphaneYorumRepository: KutuphaneYorumRepository) {

    suspend operator fun invoke(kutuphaneId: Long): MyResult<List<KutuphaneYorumRes>> {
        LoadingManager.startLoading()
        return try {
            val response = kutuphaneYorumRepository.findByKutuphaneId(kutuphaneId)
            if (response.isSuccessful) {
                val list = response.body()
                if (list != null) {
                    MyResult.Success(list)
                } else {
                    MyResult.Error(Exception("List is null"))
                }
            } else {
                MyResult.Error(Exception("Failed to find all comments: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}