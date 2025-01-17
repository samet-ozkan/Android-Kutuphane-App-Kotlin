package com.sametozkan.kutuphane.domain.usecase.yazar

import com.sametozkan.kutuphane.data.dto.response.YazarRes
import com.sametozkan.kutuphane.domain.repository.YazarRepository
import com.sametozkan.kutuphane.util.LoadingManager
import javax.inject.Inject
import com.sametozkan.kutuphane.util.MyResult

class FindYazarByIdUseCase @Inject constructor(private val yazarRepository: YazarRepository) {

    suspend operator fun invoke(id: Long): MyResult<YazarRes> {
        LoadingManager.startLoading()
        return try {
            val response = yazarRepository.findById(id)
            if (response.isSuccessful) {
                val yazarRes = response.body()
                if (yazarRes != null) {
                    MyResult.Success(yazarRes)
                } else {
                    MyResult.Error(Exception("Yazar response body is null!"), response.code())
                }
            } else {
                MyResult.Error(Exception("Failed to find yazar by id!"), response.code())
            }
        } catch (e: Exception) {
            MyResult.Error(e, null)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}