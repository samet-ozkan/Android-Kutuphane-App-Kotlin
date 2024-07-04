package com.sametozkan.kutuphane.domain.usecase.kitapkutuphane

import android.content.res.Resources.NotFoundException
import com.sametozkan.kutuphane.data.dto.request.KitapKutuphaneReq
import com.sametozkan.kutuphane.domain.repository.KitapKutuphaneRepository
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class SaveKitapKutuphaneUseCase @Inject constructor(private val kitapKutuphaneRepository: KitapKutuphaneRepository) {

    suspend operator fun invoke(kitapKutuphaneReq: KitapKutuphaneReq): MyResult<Unit> {
        //LoadingManager.startLoading()
        return try {
            val response = kitapKutuphaneRepository.save(kitapKutuphaneReq)
            if (response.isSuccessful) {
                MyResult.Success(Unit)
            } else {
                MyResult.Error(Exception("Failed to save kitap kutuphane!"), response.code())
            }
        } catch (e: Exception) {
            MyResult.Error(e, null)
        } finally {
            //LoadingManager.stopLoading()
        }
    }
}