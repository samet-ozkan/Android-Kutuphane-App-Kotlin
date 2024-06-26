package com.sametozkan.kutuphane.domain.usecase.kitapkullanici

import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import com.sametozkan.kutuphane.domain.repository.KitapKullaniciRepository
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class FindByKullaniciIdAndOnaylandiIsFalseUseCase @Inject constructor(private val kitapKullaniciRepository: KitapKullaniciRepository) {

    suspend operator fun invoke(accountId: Long): MyResult<List<KitapKullaniciRes>> {
        LoadingManager.startLoading()
        return try {
            val response = kitapKullaniciRepository.findByKullaniciIdAndOnaylandiIsFalse(accountId)
            if (response.isSuccessful) {
                val kitapKullaniciList = response.body()
                if (kitapKullaniciList != null) {
                    MyResult.Success(kitapKullaniciList)
                } else {
                    MyResult.Error(Exception("Kitap kullanici list is null"), response.code())
                }
            } else {
                MyResult.Error(Exception("Failed to find kitap kullanici list!"), response.code())
            }
        } catch (e: Exception) {
            MyResult.Error(e, null)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}