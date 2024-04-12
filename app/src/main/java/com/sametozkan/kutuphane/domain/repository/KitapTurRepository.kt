package com.sametozkan.kutuphane.domain.repository

import com.sametozkan.kutuphane.data.dto.request.KitapTurReq
import com.sametozkan.kutuphane.data.dto.response.KitapTurRes
import retrofit2.Response

interface KitapTurRepository {

    suspend fun save(kitapTurReq: KitapTurReq): Response<Unit>

    suspend fun update(id: Long, kitapTurReq: KitapTurReq): Response<KitapTurRes>

    suspend fun findById(id: Long): Response<KitapTurRes>

    suspend fun findAll(): Response<List<KitapTurRes>>
}