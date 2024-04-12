package com.sametozkan.kutuphane.domain.repository

import com.sametozkan.kutuphane.data.dto.request.KitapYazarReq
import com.sametozkan.kutuphane.data.dto.response.KitapYazarRes
import retrofit2.Response

interface KitapYazarRepository {

    suspend fun save(kitapYazarReq: KitapYazarReq): Response<Unit>

    suspend fun update(id: Long, kitapYazarReq: KitapYazarReq): Response<KitapYazarRes>

    suspend fun findById(id: Long): Response<KitapYazarRes>

    suspend fun findAll(): Response<List<KitapYazarRes>>
}