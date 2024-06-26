package com.sametozkan.kutuphane.domain.usecase.kullanici

import com.sametozkan.kutuphane.data.dto.response.KullaniciRes
import com.sametozkan.kutuphane.domain.repository.KullaniciRepository
import com.sametozkan.kutuphane.util.LoadingManager
import javax.inject.Inject
import com.sametozkan.kutuphane.util.MyResult


class FindKullaniciByIdUseCase @Inject constructor(private val kullaniciRepository: KullaniciRepository) {

    suspend operator fun invoke(id: Long): MyResult<KullaniciRes> {
        LoadingManager.startLoading()
        return try {
            val response = kullaniciRepository.findById(id)
            if (response.isSuccessful) {
                val kullaniciRes = response.body()
                if (kullaniciRes != null) {
                    MyResult.Success(kullaniciRes)
                } else {
                    MyResult.Error(Exception("Kullanici response body is null!"), response.code())
                }
            } else {
                MyResult.Error(Exception("Failed to find kullanici by id!"), response.code())
            }
        } catch (e: Exception) {
            MyResult.Error(e, null)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}