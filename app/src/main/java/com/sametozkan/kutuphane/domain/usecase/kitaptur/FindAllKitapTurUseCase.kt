package com.sametozkan.kutuphane.domain.usecase.kitaptur

import com.sametozkan.kutuphane.data.dto.response.KitapTurRes
import com.sametozkan.kutuphane.domain.repository.KitapTurRepository
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class FindAllKitapTurUseCase @Inject constructor(private val kitapTurRepository: KitapTurRepository) {

    suspend operator fun invoke(): MyResult<List<KitapTurRes>> {
        LoadingManager.startLoading()
        return try {
            val response = kitapTurRepository.findAll()
            if (response.isSuccessful) {
                val kitapTurList = response.body()
                if (kitapTurList != null) {
                    MyResult.Success(kitapTurList)
                } else {
                    MyResult.Error(Exception("Kitap tur list is null"))
                }
            } else {
                MyResult.Error(Exception("Failed to find all kitap tur: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}