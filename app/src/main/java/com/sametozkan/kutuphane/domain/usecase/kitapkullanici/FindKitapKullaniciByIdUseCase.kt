package com.sametozkan.kutuphane.domain.usecase.kitapkullanici

import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import com.sametozkan.kutuphane.domain.repository.KitapKullaniciRepository
import com.sametozkan.kutuphane.domain.usecase.Result
import javax.inject.Inject

class FindKitapKullaniciByIdUseCase @Inject constructor(private val kitapKullaniciRepository: KitapKullaniciRepository) {

    suspend operator fun invoke(id: Long): Result<KitapKullaniciRes> {
        return try {
            val response = kitapKullaniciRepository.findById(id)
            if (response.isSuccessful) {
                val kitapKullaniciRes = response.body()
                if (kitapKullaniciRes != null) {
                    Result.Success(kitapKullaniciRes)
                } else {
                    Result.Error(Exception("Kitap kullanici response body is null"))
                }
            } else {
                Result.Error(Exception("Failed to find kitap kullanici by id: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}