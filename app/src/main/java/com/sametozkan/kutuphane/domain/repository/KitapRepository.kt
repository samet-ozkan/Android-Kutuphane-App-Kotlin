package com.sametozkan.kutuphane.domain.repository

import com.sametozkan.kutuphane.data.dto.request.KitapReq
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import retrofit2.Response

interface KitapRepository {

    suspend fun save(kitapReq: KitapReq): Response<Unit>

    suspend fun update(id: Long, kitapReq: KitapReq): Response<KitapRes>

    suspend fun findById(id: Long): Response<KitapRes>

    suspend fun findAll(): Response<List<KitapRes>>
}