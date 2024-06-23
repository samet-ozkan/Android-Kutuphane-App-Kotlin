package com.sametozkan.kutuphane.domain.usecase.kitapkullanici

import com.sametozkan.kutuphane.domain.repository.KitapKullaniciRepository
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class KitapIstegiReddetUseCase @Inject constructor(private val kitapKullaniciRepository: KitapKullaniciRepository) {

    suspend operator fun invoke(kitapKullaniciId: Long): MyResult<Unit> {
        LoadingManager.startLoading()
        return try {
            val response = kitapKullaniciRepository.kitapIstegiReddet(kitapKullaniciId)
            if (response.isSuccessful) {
                MyResult.Success(Unit)
            } else {
                MyResult.Error(Exception("Failed to save kitap kullanici: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}