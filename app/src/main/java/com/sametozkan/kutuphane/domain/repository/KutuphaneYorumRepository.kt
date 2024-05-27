package com.sametozkan.kutuphane.domain.repository

import com.sametozkan.kutuphane.data.dto.request.KutuphaneYorumReq
import com.sametozkan.kutuphane.data.dto.response.KutuphaneYorumRes
import retrofit2.Response

interface KutuphaneYorumRepository {

    suspend fun save(kutuphaneYorumReq: KutuphaneYorumReq): Response<Long>

    suspend fun findByKutuphaneId(kutuphaneId: Long): Response<List<KutuphaneYorumRes>>
}