package com.sametozkan.kutuphane.domain.usecase.kitap

import com.sametozkan.kutuphane.data.dto.request.KitapReq
import com.sametozkan.kutuphane.data.dto.request.TurReq
import com.sametozkan.kutuphane.data.dto.request.YazarReq
import com.sametozkan.kutuphane.domain.repository.KitapRepository
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class SaveKitapUseCase @Inject constructor(private val kitapRepository: KitapRepository) {

    suspend operator fun invoke(
        kitapReq: KitapReq,
    ): MyResult<Long> {
        LoadingManager.startLoading()
        return try {
            val response = kitapRepository.save(kitapReq)
            if (response.isSuccessful) {
                val kitapId = response.body()
                kitapId?.let {
                    MyResult.Success(kitapId)
                } ?: kotlin.run {
                    MyResult.Error(Exception("Kitap Id is null!"))
                }
            } else {
                MyResult.Error(Exception("Failed to save kitap: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}