package com.sametozkan.kutuphane.domain.usecase.kitapyazar

import com.sametozkan.kutuphane.data.dto.request.KitapYazarReq
import com.sametozkan.kutuphane.domain.repository.KitapYazarRepository
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class SaveKitapYazarUseCase @Inject constructor(private val kitapYazarRepository: KitapYazarRepository) {

    suspend operator fun invoke(kitapYazarReq: KitapYazarReq): MyResult<Unit> {
        return try {
            val response = kitapYazarRepository.save(kitapYazarReq)
            if (response.isSuccessful) {
                MyResult.Success(Unit)
            } else {
                MyResult.Error(Exception("Failed to save kitap yazar: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        }
    }
}