package com.sametozkan.kutuphane.domain.repository

import com.sametozkan.kutuphane.data.dto.request.KitapKullaniciReq
import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import retrofit2.Response

interface KitapKullaniciRepository {

    suspend fun save(kitapKullaniciReq: KitapKullaniciReq): Response<Unit>

    suspend fun update(id: Long, kitapKullaniciReq: KitapKullaniciReq): Response<KitapKullaniciRes>

    suspend fun findAll(): Response<List<KitapKullaniciRes>>

    suspend fun findById(id: Long): Response<KitapKullaniciRes>
    suspend fun findByKullaniciIdAndOnaylandiIsNull(accountId: Long): Response<List<KitapKullaniciRes>>
    suspend fun deleteById(id: Long): Response<Unit>
    suspend fun findByKullaniciIdAndOnaylandiIsFalse(accountId: Long): Response<List<KitapKullaniciRes>>
    suspend fun findByKullaniciIdAndOnaylandiIsTrue(accountId: Long): Response<List<KitapKullaniciRes>>
    suspend fun findByKutuphaneId(accountId: Long): Response<List<KitapKullaniciRes>>
    suspend fun kitapIstegiReddet(kitapKullaniciId: Long): Response<Unit>
    suspend fun kitapIstegiOnayla(kitapKullaniciId: Long): Response<Unit>
    suspend fun teslimEdilmeyenler(kutuphaneAccountId: Long): Response<List<KitapKullaniciRes>>
    suspend fun teslimEdildi(kitapKullaniciId: Long): Response<Unit>
    suspend fun fetchRecentRecords(limit: Int): Response<List<KitapKullaniciRes>>
}