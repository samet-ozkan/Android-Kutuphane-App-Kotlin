package com.sametozkan.kutuphane.domain.usecase.kitapkutuphane

import com.sametozkan.kutuphane.data.dto.response.KitapKutuphaneRes
import com.sametozkan.kutuphane.domain.repository.KitapKutuphaneRepository
import com.sametozkan.kutuphane.domain.usecase.MyResult
import javax.inject.Inject

class FindAllKitapKutuphaneUseCase @Inject constructor(private val kitapKutuphaneRepository: KitapKutuphaneRepository) {

    suspend operator fun invoke(): MyResult<List<KitapKutuphaneRes>> {
        return try {
            val response = kitapKutuphaneRepository.findAll()
            if (response.isSuccessful) {
                val kitapKutuphaneList = response.body()
                if (kitapKutuphaneList != null) {
                    MyResult.Success(kitapKutuphaneList)
                } else {
                    MyResult.Error(Exception("Kitap kutuphane list is null"))
                }
            } else {
                MyResult.Error(Exception("Failed to find all kitap kutuphane: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        }
    }
}