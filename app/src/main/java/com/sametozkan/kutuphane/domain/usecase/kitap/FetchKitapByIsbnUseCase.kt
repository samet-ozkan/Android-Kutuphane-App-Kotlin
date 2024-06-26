package com.sametozkan.kutuphane.domain.usecase.kitap

import android.content.res.Resources
import com.sametozkan.kutuphane.data.dto.request.KitapReq
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.domain.repository.KitapRepository
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class FetchKitapByIsbnUseCase @Inject constructor(private val kitapRepository: KitapRepository) {

    suspend operator fun invoke(isbn: Long): MyResult<KitapReq> {
        LoadingManager.startLoading()
        return try {
            val response = kitapRepository.fetchByIsbn(isbn)
            if (response.isSuccessful) {
                val kitap = response.body()
                if (kitap != null) {
                    MyResult.Success(kitap)
                } else {
                    MyResult.Error(Exception("Kitap is null!"), response.code())
                }
            } else {
                MyResult.Error(Exception("Failed to find kitap!"), response.code())
            }
        } catch (e: Exception) {
            MyResult.Error(e, null)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}