package com.sametozkan.kutuphane.domain.usecase.kitapyorum

import com.sametozkan.kutuphane.data.dto.request.KitapYorumReq
import com.sametozkan.kutuphane.data.dto.request.KutuphaneYorumReq
import com.sametozkan.kutuphane.domain.repository.KitapYorumRepository
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class SaveKitapYorumUseCase @Inject constructor(private val kitapYorumRepository : KitapYorumRepository) {

    suspend operator fun invoke(kitapYorumReq: KitapYorumReq): MyResult<Unit> {
        LoadingManager.startLoading()
        return try {
            val response = kitapYorumRepository.save(kitapYorumReq)
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