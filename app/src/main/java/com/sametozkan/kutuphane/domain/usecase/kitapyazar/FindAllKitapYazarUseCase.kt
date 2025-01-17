package com.sametozkan.kutuphane.domain.usecase.kitapyazar

import com.sametozkan.kutuphane.data.dto.response.KitapYazarRes
import com.sametozkan.kutuphane.domain.repository.KitapYazarRepository
import com.sametozkan.kutuphane.util.LoadingManager
import javax.inject.Inject
import com.sametozkan.kutuphane.util.MyResult


class FindAllKitapYazarUseCase @Inject constructor(private val kitapYazarRepository: KitapYazarRepository) {

    suspend operator fun invoke(): MyResult<List<KitapYazarRes>> {
        LoadingManager.startLoading()
        return try {
            val response = kitapYazarRepository.findAll()
            if (response.isSuccessful) {
                val kitapYazarList = response.body()
                if (kitapYazarList != null) {
                    MyResult.Success(kitapYazarList)
                } else {
                    MyResult.Error(Exception("Kitap yazar list is null"), response.code())
                }
            } else {
                MyResult.Error(Exception("Failed to find all kitap yazar!"), response.code())
            }
        } catch (e: Exception) {
            MyResult.Error(e, null)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}