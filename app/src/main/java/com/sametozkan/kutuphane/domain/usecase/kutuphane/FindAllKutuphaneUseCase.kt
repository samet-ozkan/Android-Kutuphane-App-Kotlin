package com.sametozkan.kutuphane.domain.usecase.kutuphane

import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes
import com.sametozkan.kutuphane.domain.repository.KutuphaneRepository
import com.sametozkan.kutuphane.util.LoadingManager
import javax.inject.Inject
import com.sametozkan.kutuphane.util.MyResult


class FindAllKutuphaneUseCase @Inject constructor(private val kutuphaneRepository: KutuphaneRepository) {

    suspend operator fun invoke(): MyResult<List<KutuphaneRes>> {
        LoadingManager.startLoading()
        return try {
            val response = kutuphaneRepository.findAll()
            if (response.isSuccessful) {
                val kutuphaneList = response.body()
                if (kutuphaneList != null) {
                    MyResult.Success(kutuphaneList)
                } else {
                    MyResult.Error(Exception("Kutuphane list is null!"), response.code())
                }
            } else {
                println("Code: " + response.code())
                MyResult.Error(Exception("Failed to find all kutuphane!"), response.code())
            }
        } catch (e: Exception) {
            MyResult.Error(e, null)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}