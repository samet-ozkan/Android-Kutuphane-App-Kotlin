package com.sametozkan.kutuphane.domain.usecase.kitapkullanici

import com.sametozkan.kutuphane.data.dto.request.KitapKullaniciReq
import com.sametozkan.kutuphane.domain.repository.KitapKullaniciRepository
import com.sametozkan.kutuphane.domain.usecase.Result
import javax.inject.Inject

class SaveKitapKullaniciUseCase @Inject constructor(private val kitapKullaniciRepository: KitapKullaniciRepository) {

    suspend operator fun invoke(kitapKullaniciReq: KitapKullaniciReq): Result<Unit> {
        return try {
            val response = kitapKullaniciRepository.save(kitapKullaniciReq)
            if (response.isSuccessful) {
                Result.Success(Unit)
            } else {
                Result.Error(Exception("Failed to save kitap kullanici: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}