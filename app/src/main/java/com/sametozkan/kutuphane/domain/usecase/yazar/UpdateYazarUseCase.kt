package com.sametozkan.kutuphane.domain.usecase.yazar

import com.sametozkan.kutuphane.data.dto.request.YazarReq
import com.sametozkan.kutuphane.data.dto.response.YazarRes
import com.sametozkan.kutuphane.domain.repository.YazarRepository
import com.sametozkan.kutuphane.util.LoadingManager
import javax.inject.Inject
import com.sametozkan.kutuphane.util.MyResult

class UpdateYazarUseCase @Inject constructor(private val yazarRepository: YazarRepository) {

    suspend operator fun invoke(id: Long, yazarReq: YazarReq): MyResult<YazarRes> {
        LoadingManager.startLoading()
        return try {
            val response = yazarRepository.update(id, yazarReq)
            if (response.isSuccessful) {
                val yazarRes = response.body()
                if (yazarRes != null) {
                    MyResult.Success(yazarRes)
                } else {
                    MyResult.Error(Exception("Yazar response body is null!"), response.code())
                }
            } else {
                MyResult.Error(Exception("Failed to update yazar!"), response.code())
            }
        } catch (e: Exception) {
            MyResult.Error(e, null)
        } finally {
            LoadingManager.stopLoading()
        }

    }
}