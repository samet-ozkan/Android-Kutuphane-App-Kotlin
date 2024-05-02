package com.sametozkan.kutuphane.domain.usecase.kitap

import com.sametozkan.kutuphane.data.dto.request.KitapReq
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.domain.repository.KitapRepository
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class UpdateKitapUseCase @Inject constructor(private val kitapRepository: KitapRepository) {

    suspend operator fun invoke(id: Long, kitapReq: KitapReq): MyResult<KitapRes> {
        return try {
            val response = kitapRepository.update(id, kitapReq)
            if (response.isSuccessful) {
                val kitapRes = response.body()
                if (kitapRes != null) {
                    MyResult.Success(kitapRes)
                } else {
                    MyResult.Error(Exception("Kitap response body is null"))
                }
            } else {
                MyResult.Error(Exception("Failed to update kitap: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        }
    }
}