package com.sametozkan.kutuphane.domain.usecase.kitapkullanici

import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import com.sametozkan.kutuphane.domain.repository.KitapKullaniciRepository
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class FindKitapKullaniciByIdUseCase @Inject constructor(private val kitapKullaniciRepository: KitapKullaniciRepository) {

    suspend operator fun invoke(id: Long): MyResult<KitapKullaniciRes> {
        LoadingManager.startLoading()
        return try {
            val response = kitapKullaniciRepository.findById(id)
            if (response.isSuccessful) {
                val kitapKullaniciRes = response.body()
                if (kitapKullaniciRes != null) {
                    MyResult.Success(kitapKullaniciRes)
                } else {
                    MyResult.Error(Exception("Kitap kullanici response body is null!"), response.code())
                }
            } else {
                MyResult.Error(Exception("Failed to find kitap kullanici by id!"), response.code())
            }
        } catch (e: Exception) {
            MyResult.Error(e, null)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}