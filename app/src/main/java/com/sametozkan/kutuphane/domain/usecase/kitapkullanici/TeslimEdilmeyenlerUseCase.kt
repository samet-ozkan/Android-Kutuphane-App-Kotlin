package com.sametozkan.kutuphane.domain.usecase.kitapkullanici

import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import com.sametozkan.kutuphane.domain.repository.KitapKullaniciRepository
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class TeslimEdilmeyenlerUseCase @Inject constructor(private val kitapKullaniciRepository: KitapKullaniciRepository) {

    suspend operator fun invoke(kutuphaneId: Long): MyResult<List<KitapKullaniciRes>> {
        return try {
            val response = kitapKullaniciRepository.teslimEdilmeyenler(kutuphaneId)
            if (response.isSuccessful) {
                val kitapKullaniciRes = response.body()
                if (kitapKullaniciRes != null) {
                    MyResult.Success(kitapKullaniciRes)
                } else {
                    MyResult.Error(Exception("Kitap kullanici response body is null"))
                }
            } else {
                MyResult.Error(Exception("Failed to find kitap kullanici by id: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        }
    }
}