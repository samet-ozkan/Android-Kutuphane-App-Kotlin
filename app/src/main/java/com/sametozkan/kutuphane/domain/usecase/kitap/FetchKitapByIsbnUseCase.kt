package com.sametozkan.kutuphane.domain.usecase.kitap

import android.content.res.Resources
import com.sametozkan.kutuphane.data.dto.request.KitapReq
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.domain.repository.KitapRepository
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class FetchKitapByIsbnUseCase @Inject constructor(private val kitapRepository: KitapRepository) {

    suspend operator fun invoke(isbn: Long): MyResult<KitapReq> {
        return try {
            val response = kitapRepository.fetchByIsbn(isbn)
            if (response.isSuccessful) {
                val kitap = response.body()
                if (kitap != null) {
                    MyResult.Success(kitap)
                } else {
                    MyResult.Error(Resources.NotFoundException())
                }
            } else {
                MyResult.Error(Exception("Failed to find kitap: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        }
    }
}