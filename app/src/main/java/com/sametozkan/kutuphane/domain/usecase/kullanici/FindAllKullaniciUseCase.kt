package com.sametozkan.kutuphane.domain.usecase.kullanici

import com.sametozkan.kutuphane.data.dto.response.KullaniciRes
import com.sametozkan.kutuphane.domain.repository.KullaniciRepository
import com.sametozkan.kutuphane.util.LoadingManager
import javax.inject.Inject
import com.sametozkan.kutuphane.util.MyResult


class FindAllKullaniciUseCase @Inject constructor(private val kullaniciRepository: KullaniciRepository) {

    suspend operator fun invoke(): MyResult<List<KullaniciRes>> {
        LoadingManager.startLoading()
        return try {
            val response = kullaniciRepository.findAll()
            if (response.isSuccessful) {
                val kullaniciList = response.body()
                if (kullaniciList != null) {
                    MyResult.Success(kullaniciList)
                } else {
                    MyResult.Error(Exception("Kullanici list is null!"), response.code())
                }
            } else {
                MyResult.Error(Exception("Failed to find all kullanici!"), response.code())
            }
        } catch (e: Exception) {
            MyResult.Error(e, null)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}