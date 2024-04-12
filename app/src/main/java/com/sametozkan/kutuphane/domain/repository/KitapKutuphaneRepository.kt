package com.sametozkan.kutuphane.domain.repository

import com.sametozkan.kutuphane.data.dto.request.KitapKutuphaneReq
import com.sametozkan.kutuphane.data.dto.response.KitapKutuphaneRes
import retrofit2.Response

interface KitapKutuphaneRepository {

    suspend fun save(kitapKutuphaneReq: KitapKutuphaneReq): Response<Unit>

    suspend fun update(id: Long, kitapKutuphaneReq: KitapKutuphaneReq): Response<KitapKutuphaneRes>

    suspend fun findById(id: Long): Response<KitapKutuphaneRes>

    suspend fun findAll(): Response<List<KitapKutuphaneRes>>
}