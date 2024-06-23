package com.sametozkan.kutuphane.domain.usecase.tur

import com.sametozkan.kutuphane.data.dto.request.TurReq
import com.sametozkan.kutuphane.domain.repository.TurRepository
import com.sametozkan.kutuphane.util.LoadingManager
import javax.inject.Inject
import com.sametozkan.kutuphane.util.MyResult


class SaveTurUseCase @Inject constructor(private val turRepository: TurRepository) {

    suspend operator fun invoke(turReq: TurReq): MyResult<Unit> {
        LoadingManager.startLoading()
        return try {
            val response = turRepository.save(turReq)
            if (response.isSuccessful) {
                MyResult.Success(Unit)
            } else {
                MyResult.Error(Exception("Failed to save tur: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}