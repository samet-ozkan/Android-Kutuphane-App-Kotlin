package com.sametozkan.kutuphane.domain.usecase.kitapkutuphane

import com.sametozkan.kutuphane.data.dto.request.KitapKutuphaneReq
import com.sametozkan.kutuphane.data.dto.response.KitapKutuphaneRes
import com.sametozkan.kutuphane.domain.repository.KitapKutuphaneRepository
import com.sametozkan.kutuphane.domain.usecase.MyResult
import javax.inject.Inject

class UpdateKitapKutuphaneUseCase @Inject constructor(private val kitapKutuphaneRepository: KitapKutuphaneRepository) {

    suspend operator fun invoke(id: Long, kitapKutuphaneReq: KitapKutuphaneReq): MyResult<KitapKutuphaneRes> {
        return try {
            val response = kitapKutuphaneRepository.update(id, kitapKutuphaneReq)
            if (response.isSuccessful) {
                val kitapKutuphaneRes = response.body()
                if (kitapKutuphaneRes != null) {
                    MyResult.Success(kitapKutuphaneRes)
                } else {
                    MyResult.Error(Exception("Kitap kutuphane response body is null"))
                }
            } else {
                MyResult.Error(Exception("Failed to update kitap kutuphane: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        }
    }
}