package com.sametozkan.kutuphane.domain.usecase.kitapkutuphane

import com.sametozkan.kutuphane.data.dto.response.KitapKutuphaneRes
import com.sametozkan.kutuphane.domain.repository.KitapKutuphaneRepository
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class FindAllKitapKutuphaneUseCase @Inject constructor(private val kitapKutuphaneRepository: KitapKutuphaneRepository) {

    suspend operator fun invoke(): MyResult<List<KitapKutuphaneRes>> {
        LoadingManager.startLoading()
        return try {
            val response = kitapKutuphaneRepository.findAll()
            if (response.isSuccessful) {
                val kitapKutuphaneList = response.body()
                if (kitapKutuphaneList != null) {
                    MyResult.Success(kitapKutuphaneList)
                } else {
                    MyResult.Error(Exception("Kitap kutuphane list is null!"), response.code())
                }
            } else {
                MyResult.Error(Exception("Failed to find all kitap kutuphane!"), response.code())
            }
        } catch (e: Exception) {
            MyResult.Error(e, null)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}