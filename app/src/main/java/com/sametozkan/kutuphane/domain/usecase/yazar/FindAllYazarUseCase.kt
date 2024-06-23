package com.sametozkan.kutuphane.domain.usecase.yazar

import com.sametozkan.kutuphane.data.dto.response.YazarRes
import com.sametozkan.kutuphane.domain.repository.YazarRepository
import com.sametozkan.kutuphane.util.LoadingManager
import javax.inject.Inject
import com.sametozkan.kutuphane.util.MyResult

class FindAllYazarUseCase @Inject constructor(private val yazarRepository: YazarRepository) {

    suspend operator fun invoke(): MyResult<List<YazarRes>> {
        LoadingManager.startLoading()
        return try {
            val response = yazarRepository.findAll()
            if (response.isSuccessful) {
                val yazarList = response.body()
                if (yazarList != null) {
                    MyResult.Success(yazarList)
                } else {
                    MyResult.Error(Exception("Yazar list is null"))
                }
            } else {
                MyResult.Error(Exception("Failed to find all yazar: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}