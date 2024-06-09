package com.sametozkan.kutuphane.domain.usecase.kullanici

import com.sametozkan.kutuphane.data.dto.response.KullaniciRes
import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes
import com.sametozkan.kutuphane.domain.repository.KullaniciRepository
import com.sametozkan.kutuphane.domain.repository.KutuphaneRepository
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class FindKullaniciByAccountIdUseCase @Inject constructor(private val kullaniciRepository: KullaniciRepository) {

    suspend operator fun invoke(accountId: Long): MyResult<KullaniciRes> {
        return try {
            val response = kullaniciRepository.findByAccountId(accountId)
            if (response.isSuccessful) {
                val kullaniciRes = response.body()
                if (kullaniciRes != null) {
                    MyResult.Success(kullaniciRes)
                } else {
                    MyResult.Error(Exception("Kullanici response body is null"))
                }
            } else {
                MyResult.Error(Exception("Failed to find kullanici by account id: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        }
    }
}