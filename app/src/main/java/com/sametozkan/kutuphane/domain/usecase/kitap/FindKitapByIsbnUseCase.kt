package com.sametozkan.kutuphane.domain.usecase.kitap

import android.content.res.Resources.NotFoundException
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.domain.repository.KitapRepository
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class FindKitapByIsbnUseCase @Inject constructor(private val kitapRepository: KitapRepository) {

    suspend operator fun invoke(isbn: Long): MyResult<KitapRes> {
        return try {
            val response = kitapRepository.findByIsbn(isbn)
            if (response.isSuccessful) {
                val kitap = response.body()
                if (kitap != null) {
                    MyResult.Success(kitap)
                } else {
                    MyResult.Error(Exception())
                }
            } else if (response.code() == 404) {
                MyResult.Error(NotFoundException())
            } else {
                MyResult.Error(Exception())
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        }
    }
}