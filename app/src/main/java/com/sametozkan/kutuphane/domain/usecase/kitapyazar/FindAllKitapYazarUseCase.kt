package com.sametozkan.kutuphane.domain.usecase.kitapyazar

import com.sametozkan.kutuphane.data.dto.response.KitapYazarRes
import com.sametozkan.kutuphane.domain.repository.KitapYazarRepository
import javax.inject.Inject
import com.sametozkan.kutuphane.domain.usecase.Result


class FindAllKitapYazarUseCase @Inject constructor(private val kitapYazarRepository: KitapYazarRepository) {

    suspend operator fun invoke(): Result<List<KitapYazarRes>> {
        return try {
            val response = kitapYazarRepository.findAll()
            if (response.isSuccessful) {
                val kitapYazarList = response.body()
                if (kitapYazarList != null) {
                    Result.Success(kitapYazarList)
                } else {
                    Result.Error(Exception("Kitap yazar list is null"))
                }
            } else {
                Result.Error(Exception("Failed to find all kitap yazar: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}