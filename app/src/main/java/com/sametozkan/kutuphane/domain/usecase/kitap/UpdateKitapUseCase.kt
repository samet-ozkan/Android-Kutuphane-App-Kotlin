package com.sametozkan.kutuphane.domain.usecase.kitap

import com.sametozkan.kutuphane.R
import com.sametozkan.kutuphane.data.dto.request.KitapReq
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.domain.repository.KitapRepository
import com.sametozkan.kutuphane.presentation.dialog.ErrorDialog
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class UpdateKitapUseCase @Inject constructor(private val kitapRepository: KitapRepository) {

    suspend operator fun invoke(id: Long, kitapReq: KitapReq): MyResult<KitapRes> {
        LoadingManager.startLoading()
        return try {
            val response = kitapRepository.update(id, kitapReq)
            if (response.isSuccessful) {
                val kitapRes = response.body()
                if (kitapRes != null) {
                    MyResult.Success(kitapRes)
                } else {
                    MyResult.Error(Exception("Kitap is null!"), response.code())
                }
            } else {
                MyResult.Error(Exception("Failed to update kitap!"), response.code());
            }
        } catch (e: Exception) {
            MyResult.Error(e, null);
        } finally {
            LoadingManager.stopLoading()
        }
    }
}