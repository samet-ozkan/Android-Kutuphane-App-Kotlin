package com.sametozkan.kutuphane.domain.usecase.kitapkullanici

import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import com.sametozkan.kutuphane.domain.repository.KitapKullaniciRepository
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class FindAllKitapKullaniciUseCase @Inject constructor(private val kitapKullaniciRepository: KitapKullaniciRepository) {

    suspend operator fun invoke(): MyResult<List<KitapKullaniciRes>> {
        LoadingManager.startLoading()
        return try {
            val response = kitapKullaniciRepository.findAll()
            if (response.isSuccessful) {
                val kitapKullaniciList = response.body()
                if (kitapKullaniciList != null) {
                    MyResult.Success(kitapKullaniciList)
                } else {
                    MyResult.Error(Exception("Kitap kullanici list is null"))
                }
            } else {
                MyResult.Error(Exception("Failed to find all kitap kullanici: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}