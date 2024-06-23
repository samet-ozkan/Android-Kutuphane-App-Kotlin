package com.sametozkan.kutuphane.domain.usecase.kitapkutuphane

import com.sametozkan.kutuphane.data.dto.response.KitapKutuphaneRes
import com.sametozkan.kutuphane.domain.repository.KitapKutuphaneRepository
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class FindKitapKutuphaneByIdUseCase @Inject constructor(private val kitapKutuphaneRepository: KitapKutuphaneRepository) {

    suspend operator fun invoke(id: Long): MyResult<KitapKutuphaneRes> {
        LoadingManager.startLoading()
        return try {
            val response = kitapKutuphaneRepository.findById(id)
            if (response.isSuccessful) {
                val kitapKutuphaneRes = response.body()
                if (kitapKutuphaneRes != null) {
                    MyResult.Success(kitapKutuphaneRes)
                } else {
                    MyResult.Error(Exception("Kitap kutuphane response body is null"))
                }
            } else {
                MyResult.Error(Exception("Failed to find kitap kutuphane by id: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}