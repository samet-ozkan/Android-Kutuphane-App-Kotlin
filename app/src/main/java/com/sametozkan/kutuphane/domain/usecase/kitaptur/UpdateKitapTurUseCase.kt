package com.sametozkan.kutuphane.domain.usecase.kitaptur

import com.sametozkan.kutuphane.data.dto.request.KitapTurReq
import com.sametozkan.kutuphane.data.dto.response.KitapTurRes
import com.sametozkan.kutuphane.domain.repository.KitapTurRepository
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class UpdateKitapTurUseCase @Inject constructor(private val kitapTurRepository: KitapTurRepository) {

    suspend operator fun invoke(id: Long, kitapTurReq: KitapTurReq): MyResult<KitapTurRes> {
        LoadingManager.startLoading()
        return try {
            val response = kitapTurRepository.update(id, kitapTurReq)
            if (response.isSuccessful) {
                val kitapTurRes = response.body()
                if (kitapTurRes != null) {
                    MyResult.Success(kitapTurRes)
                } else {
                    MyResult.Error(Exception("Kitap tur response body is null!"), response.code())
                }
            } else {
                MyResult.Error(Exception("Failed to update kitap tur!"), response.code())
            }
        } catch (e: Exception) {
            MyResult.Error(e, null)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}