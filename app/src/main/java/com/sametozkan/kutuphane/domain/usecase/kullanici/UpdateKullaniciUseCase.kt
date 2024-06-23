package com.sametozkan.kutuphane.domain.usecase.kullanici

import com.sametozkan.kutuphane.data.dto.request.KullaniciReq
import com.sametozkan.kutuphane.data.dto.response.KullaniciRes
import com.sametozkan.kutuphane.domain.repository.KullaniciRepository
import com.sametozkan.kutuphane.util.LoadingManager
import javax.inject.Inject
import com.sametozkan.kutuphane.util.MyResult

class UpdateKullaniciUseCase @Inject constructor(private val kullaniciRepository: KullaniciRepository) {

    suspend operator fun invoke(id: Long, kullaniciReq: KullaniciReq): MyResult<KullaniciRes> {
        LoadingManager.startLoading()
        return try {
            val response = kullaniciRepository.update(id, kullaniciReq)
            if (response.isSuccessful) {
                val kullaniciRes = response.body()
                if (kullaniciRes != null) {
                    MyResult.Success(kullaniciRes)
                } else {
                    MyResult.Error(Exception("Kullanici response body is null"))
                }
            } else {
                MyResult.Error(Exception("Failed to update kullanici: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}