package com.sametozkan.kutuphane.domain.usecase.kitapkullanici
import com.sametozkan.kutuphane.domain.repository.KitapKullaniciRepository
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class TeslimEdildiUseCase @Inject constructor(private val kitapKullaniciRepository: KitapKullaniciRepository) {

    suspend operator fun invoke(kitapKullaniciId: Long): MyResult<Unit> {
        return try {
            val response = kitapKullaniciRepository.teslimEdildi(kitapKullaniciId)
            if (response.isSuccessful) {
                    MyResult.Success(Unit)
            } else {
                MyResult.Error(Exception("Failed to find kitap kullanici by id: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        }
    }
}