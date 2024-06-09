package com.sametozkan.kutuphane.domain.repository

import com.sametozkan.kutuphane.data.dto.request.KitapYorumReq
import com.sametozkan.kutuphane.data.dto.response.KitapYorumRes
import retrofit2.Response

interface KitapYorumRepository {
    suspend fun save(kitapYorumReq: KitapYorumReq): Response<Long>
    suspend fun findByKitapId(kitapId: Long): Response<List<KitapYorumRes>>
}