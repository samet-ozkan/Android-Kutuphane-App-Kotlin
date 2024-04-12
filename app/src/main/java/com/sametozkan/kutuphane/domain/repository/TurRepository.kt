package com.sametozkan.kutuphane.domain.repository

import com.sametozkan.kutuphane.data.dto.request.TurReq
import com.sametozkan.kutuphane.data.dto.response.TurRes
import retrofit2.Response

interface TurRepository {

    suspend fun save(turReq: TurReq): Response<Unit>

    suspend fun update(id: Long, turReq: TurReq): Response<TurRes>

    suspend fun findById(id: Long): Response<TurRes>

    suspend fun findAll(): Response<List<TurRes>>
}