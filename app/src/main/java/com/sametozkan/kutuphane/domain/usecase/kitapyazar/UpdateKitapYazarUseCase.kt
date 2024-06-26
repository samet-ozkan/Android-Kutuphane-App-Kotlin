package com.sametozkan.kutuphane.domain.usecase.kitapyazar

import com.sametozkan.kutuphane.data.dto.request.KitapYazarReq
import com.sametozkan.kutuphane.data.dto.response.KitapYazarRes
import com.sametozkan.kutuphane.domain.repository.KitapYazarRepository
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class UpdateKitapYazarUseCase @Inject constructor(private val kitapYazarRepository: KitapYazarRepository) {

    suspend operator fun invoke(id: Long, kitapYazarReq: KitapYazarReq): MyResult<KitapYazarRes> {
        LoadingManager.startLoading()
        return try {
            val response = kitapYazarRepository.update(id, kitapYazarReq)
            if (response.isSuccessful) {
                val kitapYazarRes = response.body()
                if (kitapYazarRes != null) {
                    MyResult.Success(kitapYazarRes)
                } else {
                    MyResult.Error(Exception("Kitap yazar response body is null!"), response.code())
                }
            } else {
                MyResult.Error(Exception("Failed to update kitap yazar!"), response.code())
            }
        } catch (e: Exception) {
            MyResult.Error(e, null)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}