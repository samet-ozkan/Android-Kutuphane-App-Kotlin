package com.sametozkan.kutuphane.domain.usecase.kitapkullanici

import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import com.sametozkan.kutuphane.domain.repository.KitapKullaniciRepository
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class FetchRecentKitapKullaniciRecordsUseCase @Inject constructor(private val kitapKullaniciRepository: KitapKullaniciRepository) {

    suspend operator fun invoke(limit: Int) : MyResult<List<KitapKullaniciRes>>{
        LoadingManager.startLoading()
        return try {
            val result = kitapKullaniciRepository.fetchRecentRecords(limit)
            if(result.isSuccessful){
                result.body()?.let {
                    MyResult.Success(it)
                } ?: kotlin.run {
                    MyResult.Error(Exception("Kitap kullanici list is null!"), result.code())
                }
            }
            else{
                MyResult.Error(Exception("Failed to fetch recent records in kitap kullanici!"), result.code())
            }
        }
        catch (e: Exception){
            MyResult.Error(e, null)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}