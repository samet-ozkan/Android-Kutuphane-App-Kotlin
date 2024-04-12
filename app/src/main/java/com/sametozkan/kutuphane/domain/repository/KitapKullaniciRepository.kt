package com.sametozkan.kutuphane.domain.repository

import com.sametozkan.kutuphane.data.dto.request.KitapKullaniciReq
import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import retrofit2.Response

interface KitapKullaniciRepository {

    suspend fun save(kitapKullaniciReq: KitapKullaniciReq): Response<Unit>

    suspend fun update(id: Long, kitapKullaniciReq: KitapKullaniciReq): Response<KitapKullaniciRes>

    suspend fun findAll(): Response<List<KitapKullaniciRes>>

    suspend fun findById(id: Long): Response<KitapKullaniciRes>
}