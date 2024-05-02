package com.sametozkan.kutuphane.domain.usecase.kullanici

import com.sametozkan.kutuphane.data.dto.response.KullaniciRes
import com.sametozkan.kutuphane.domain.repository.KullaniciRepository
import javax.inject.Inject
import com.sametozkan.kutuphane.util.MyResult


class FindAllKullaniciUseCase @Inject constructor(private val kullaniciRepository: KullaniciRepository) {

    suspend operator fun invoke(): MyResult<List<KullaniciRes>> {
        return try {
            val response = kullaniciRepository.findAll()
            if (response.isSuccessful) {
                val kullaniciList = response.body()
                if (kullaniciList != null) {
                    MyResult.Success(kullaniciList)
                } else {
                    MyResult.Error(Exception("Kullanici list is null"))
                }
            } else {
                MyResult.Error(Exception("Failed to find all kullanici: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        }
    }
}