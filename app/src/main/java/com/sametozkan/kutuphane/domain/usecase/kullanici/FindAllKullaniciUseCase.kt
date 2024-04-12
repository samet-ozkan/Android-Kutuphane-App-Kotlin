package com.sametozkan.kutuphane.domain.usecase.kullanici

import com.sametozkan.kutuphane.data.dto.response.KullaniciRes
import com.sametozkan.kutuphane.domain.repository.KullaniciRepository
import javax.inject.Inject
import com.sametozkan.kutuphane.domain.usecase.Result


class FindAllKullaniciUseCase @Inject constructor(private val kullaniciRepository: KullaniciRepository) {

    suspend operator fun invoke(): Result<List<KullaniciRes>> {
        return try {
            val response = kullaniciRepository.findAll()
            if (response.isSuccessful) {
                val kullaniciList = response.body()
                if (kullaniciList != null) {
                    Result.Success(kullaniciList)
                } else {
                    Result.Error(Exception("Kullanici list is null"))
                }
            } else {
                Result.Error(Exception("Failed to find all kullanici: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}