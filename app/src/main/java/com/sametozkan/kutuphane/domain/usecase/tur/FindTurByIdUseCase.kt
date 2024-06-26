package com.sametozkan.kutuphane.domain.usecase.tur

import com.sametozkan.kutuphane.data.dto.response.TurRes
import com.sametozkan.kutuphane.domain.repository.TurRepository
import com.sametozkan.kutuphane.util.LoadingManager
import javax.inject.Inject
import com.sametozkan.kutuphane.util.MyResult

class FindTurByIdUseCase @Inject constructor(private val turRepository: TurRepository) {

    suspend operator fun invoke(id: Long): MyResult<TurRes> {
        LoadingManager.startLoading()
        return try {
            val response = turRepository.findById(id)
            if (response.isSuccessful) {
                val turRes = response.body()
                if (turRes != null) {
                    MyResult.Success(turRes)
                } else {
                    MyResult.Error(Exception("Tur response body is null!"), response.code())
                }
            } else {
                MyResult.Error(Exception("Failed to find tur by id!"), response.code())
            }
        } catch (e: Exception) {
            MyResult.Error(e, null)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}