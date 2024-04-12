package com.sametozkan.kutuphane.domain.repository

import com.sametozkan.kutuphane.data.dto.request.KutuphaneReq
import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes
import retrofit2.Response

interface KutuphaneRepository {

    suspend fun update(id: Long, kutuphaneReq: KutuphaneReq): Response<KutuphaneRes>

    suspend fun findById(id: Long): Response<KutuphaneRes>

    suspend fun findAll(): Response<List<KutuphaneRes>>
}