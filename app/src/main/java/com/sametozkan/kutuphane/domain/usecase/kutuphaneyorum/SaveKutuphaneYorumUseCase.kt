package com.sametozkan.kutuphane.domain.usecase.kutuphaneyorum

import com.sametozkan.kutuphane.data.dto.request.KutuphaneYorumReq
import com.sametozkan.kutuphane.data.dto.request.YazarReq
import com.sametozkan.kutuphane.domain.repository.KutuphaneYorumRepository
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class SaveKutuphaneYorumUseCase @Inject constructor(private val kutuphaneYorumRepository : KutuphaneYorumRepository) {

    suspend operator fun invoke(kutuphaneYorumReq: KutuphaneYorumReq): MyResult<Unit> {
        LoadingManager.startLoading()
        return try {
            val response = kutuphaneYorumRepository.save(kutuphaneYorumReq)
            if (response.isSuccessful) {
                MyResult.Success(Unit)
            } else {
                MyResult.Error(Exception("Failed to save yorum!"), response.code())
            }
        } catch (e: Exception) {
            MyResult.Error(e, null)
        } finally {
            LoadingManager.stopLoading()
        }
    }

}