package com.sametozkan.kutuphane.domain.repository

import com.sametozkan.kutuphane.data.dto.request.YazarReq
import com.sametozkan.kutuphane.data.dto.response.YazarRes
import retrofit2.Response

interface YazarRepository {
    suspend fun save(yazarReq: YazarReq): Response<Unit>
    suspend fun update(id: Long, yazarReq: YazarReq): Response<YazarRes>
    suspend fun findAll(): Response<List<YazarRes>>
    suspend fun findById(id: Long): Response<YazarRes>
}