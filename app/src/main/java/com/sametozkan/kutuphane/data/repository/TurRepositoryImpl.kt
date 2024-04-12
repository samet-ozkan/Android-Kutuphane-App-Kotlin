package com.sametozkan.kutuphane.data.repository

import com.sametozkan.kutuphane.data.datasource.remote.TurService
import com.sametozkan.kutuphane.data.dto.request.TurReq
import com.sametozkan.kutuphane.data.dto.response.TurRes
import com.sametozkan.kutuphane.domain.repository.TurRepository
import retrofit2.Response

class TurRepositoryImpl(private val turService: TurService) : TurRepository {

    override suspend fun save(turReq: TurReq): Response<Unit> {
        return turService.save(turReq)
    }

    override suspend fun update(id: Long, turReq: TurReq): Response<TurRes> {
        return turService.update(id, turReq)
    }

    override suspend fun findById(id: Long): Response<TurRes> {
        return turService.findById(id)
    }

    override suspend fun findAll(): Response<List<TurRes>> {
        return turService.findAll()
    }
}