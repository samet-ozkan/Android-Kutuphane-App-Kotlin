package com.sametozkan.kutuphane.domain.usecase.kitapyazar

import com.sametozkan.kutuphane.data.dto.response.KitapYazarRes
import com.sametozkan.kutuphane.domain.repository.KitapYazarRepository
import com.sametozkan.kutuphane.util.LoadingManager
import javax.inject.Inject
import com.sametozkan.kutuphane.util.MyResult


class FindKitapYazarByIdUseCase @Inject constructor(private val kitapYazarRepository: KitapYazarRepository) {

    suspend operator fun invoke(id: Long): MyResult<KitapYazarRes> {
        LoadingManager.startLoading()
        return try {
            val response = kitapYazarRepository.findById(id)
            if (response.isSuccessful) {
                val kitapYazarRes = response.body()
                if (kitapYazarRes != null) {
                    MyResult.Success(kitapYazarRes)
                } else {
                    MyResult.Error(Exception("Kitap yazar response body is null"))
                }
            } else {
                MyResult.Error(Exception("Failed to find kitap yazar by id: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}