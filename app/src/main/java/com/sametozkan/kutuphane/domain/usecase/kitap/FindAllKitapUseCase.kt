package com.sametozkan.kutuphane.domain.usecase.kitap

import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.domain.repository.KitapRepository
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class FindAllKitapUseCase @Inject constructor(private val kitapRepository: KitapRepository) {

    suspend operator fun invoke(): MyResult<List<KitapRes>> {
        return try {
            val response = kitapRepository.findAll()
            if (response.isSuccessful) {
                val kitapList = response.body()
                if (kitapList != null) {
                    MyResult.Success(kitapList)
                } else {
                    MyResult.Error(Exception("Kitap list is null"))
                }
            } else {
                MyResult.Error(Exception("Failed to find all kitap: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        }
    }
}