package com.sametozkan.kutuphane.domain.repository

import com.sametozkan.kutuphane.data.dto.request.KitapKullaniciReq
import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import retrofit2.Response

interface KitapKullaniciRepository {

    suspend fun save(kitapKullaniciReq: KitapKullaniciReq): Response<Unit>

    suspend fun update(id: Long, kitapKullaniciReq: KitapKullaniciReq): Response<KitapKullaniciRes>

    suspend fun findAll(): Response<List<KitapKullaniciRes>>

    suspend fun findById(id: Long): Response<KitapKullaniciRes>
    suspend fun findByKullaniciIdAndIadeDurumuIsNull(accountId: Long): Response<List<KitapKullaniciRes>>
    suspend fun deleteById(id: Long): Response<Unit>
    suspend fun findByKullaniciIdAndIadeDurumuIsFalse(accountId: Long): Response<List<KitapKullaniciRes>>
    suspend fun findByKullaniciIdAndIadeDurumuIsTrue(accountId: Long): Response<List<KitapKullaniciRes>>
}