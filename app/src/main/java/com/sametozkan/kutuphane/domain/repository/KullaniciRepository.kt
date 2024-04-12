package com.sametozkan.kutuphane.domain.repository

import com.sametozkan.kutuphane.data.dto.request.KullaniciReq
import com.sametozkan.kutuphane.data.dto.response.KullaniciRes
import retrofit2.Response

interface KullaniciRepository {

    suspend fun update(id: Long, kullaniciReq: KullaniciReq): Response<KullaniciRes>

    suspend fun findById(id: Long): Response<KullaniciRes>

    suspend fun findAll(): Response<List<KullaniciRes>>
}