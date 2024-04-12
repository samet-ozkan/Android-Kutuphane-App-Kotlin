package com.sametozkan.kutuphane.domain.usecase.kitapkullanici

import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import com.sametozkan.kutuphane.domain.repository.KitapKullaniciRepository
import com.sametozkan.kutuphane.domain.usecase.Result
import javax.inject.Inject

class FindAllKitapKullaniciUseCase @Inject constructor(private val kitapKullaniciRepository: KitapKullaniciRepository) {

    suspend operator fun invoke(): Result<List<KitapKullaniciRes>> {
        return try {
            val response = kitapKullaniciRepository.findAll()
            if (response.isSuccessful) {
                val kitapKullaniciList = response.body()
                if (kitapKullaniciList != null) {
                    Result.Success(kitapKullaniciList)
                } else {
                    Result.Error(Exception("Kitap kullanici list is null"))
                }
            } else {
                Result.Error(Exception("Failed to find all kitap kullanici: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}