package com.sametozkan.kutuphane.data.repository

import com.sametozkan.kutuphane.data.datasource.remote.YazarService
import com.sametozkan.kutuphane.data.dto.request.YazarReq
import com.sametozkan.kutuphane.data.dto.response.YazarRes
import com.sametozkan.kutuphane.domain.repository.YazarRepository
import retrofit2.Response

class YazarRepositoryImpl(private val yazarService: YazarService) : YazarRepository {
    override suspend fun save(yazarReq: YazarReq): Response<Unit> =
        yazarService.save(yazarReq)

    override suspend fun update(id: Long, yazarReq: YazarReq): Response<YazarRes> =
        yazarService.update(id, yazarReq)

    override suspend fun findAll(): Response<List<YazarRes>> =
        yazarService.findAll()

    override suspend fun findById(id: Long): Response<YazarRes> =
        yazarService.findById(id)
}