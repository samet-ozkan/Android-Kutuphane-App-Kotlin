package com.sametozkan.kutuphane.domain.usecase.kitap

import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.domain.repository.KitapRepository
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class FindKitapByIdUseCase @Inject constructor(private val kitapRepository: KitapRepository) {

    suspend operator fun invoke(id: Long): MyResult<KitapRes> {
        LoadingManager.startLoading()
        return try {
            val response = kitapRepository.findById(id)
            if (response.isSuccessful) {
                val kitapRes = response.body()
                if (kitapRes != null) {
                    MyResult.Success(kitapRes)
                } else {
                    MyResult.Error(Exception("Kitap response body is null!"), response.code())
                }
            } else {
                MyResult.Error(Exception("Failed to find kitap by id!"), response.code())
            }
        } catch (e: Exception) {
            MyResult.Error(e, null)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}