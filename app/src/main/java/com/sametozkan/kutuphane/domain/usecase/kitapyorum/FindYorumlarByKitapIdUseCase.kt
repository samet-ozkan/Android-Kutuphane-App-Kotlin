package com.sametozkan.kutuphane.domain.usecase.kitapyorum

import com.sametozkan.kutuphane.data.dto.response.KitapYorumRes
import com.sametozkan.kutuphane.domain.repository.KitapYorumRepository
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class FindYorumlarByKitapIdUseCase @Inject constructor(private val kitapYorumRepository: KitapYorumRepository) {

    suspend operator fun invoke(kitapId: Long): MyResult<List<KitapYorumRes>> {
        LoadingManager.startLoading()
        return try {
            val response = kitapYorumRepository.findByKitapId(kitapId)
            if (response.isSuccessful) {
                val list = response.body()
                if (list != null) {
                    MyResult.Success(list)
                } else {
                    MyResult.Error(Exception("Kitap yorum list is null"), response.code())
                }
            } else {
                MyResult.Error(Exception("Failed to find all comments!"), response.code())
            }
        } catch (e: Exception) {
            MyResult.Error(e, null)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}