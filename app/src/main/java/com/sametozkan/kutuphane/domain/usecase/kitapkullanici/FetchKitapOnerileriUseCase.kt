package com.sametozkan.kutuphane.domain.usecase.kitapkullanici

import com.sametozkan.kutuphane.data.dto.response.KitapOnerisiRes
import com.sametozkan.kutuphane.domain.repository.KitapKullaniciRepository
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class FetchKitapOnerileriUseCase @Inject constructor(private val kitapKullaniciRepository: KitapKullaniciRepository) {

    suspend operator fun invoke(accountId: Long): MyResult<List<KitapOnerisiRes>> {
        return try {
            val response = kitapKullaniciRepository.fetchKitapOnerileri(accountId)
            if (response.isSuccessful) {
                val kitapOnerileri = response.body()
                if (kitapOnerileri != null) {
                    MyResult.Success(kitapOnerileri)
                } else {
                    MyResult.Error(Exception("Kitap onerileri list is null"))
                }
            } else {
                MyResult.Error(Exception("Failed to find kitap onerileri list: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        }
    }
}