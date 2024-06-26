package com.sametozkan.kutuphane.domain.usecase.kitap

import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.domain.repository.KitapRepository
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class FindAllKitapUseCase @Inject constructor(private val kitapRepository: KitapRepository) {

    suspend operator fun invoke(): MyResult<List<KitapRes>> {
        LoadingManager.startLoading()
        return try {
            val response = kitapRepository.findAll()
            if (response.isSuccessful) {
                val kitapList = response.body()
                if (kitapList != null) {
                    MyResult.Success(kitapList)
                } else {
                    MyResult.Error(Exception("Kitap list is null"), response.code())
                }
            } else {
                MyResult.Error(Exception("Failed to find all kitap!"), response.code())
            }
        } catch (e: Exception) {
            MyResult.Error(e, null)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}