package com.sametozkan.kutuphane.data.repository

import com.sametozkan.kutuphane.data.datasource.remote.KutuphaneService
import com.sametozkan.kutuphane.data.dto.request.KutuphaneReq
import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes
import com.sametozkan.kutuphane.domain.repository.KutuphaneRepository
import retrofit2.Response

class KutuphaneRepositoryImpl(private val kutuphaneService: KutuphaneService) :
    KutuphaneRepository {

    override suspend fun update(id: Long, kutuphaneReq: KutuphaneReq): Response<KutuphaneRes> {
        return kutuphaneService.update(id, kutuphaneReq)
    }

    override suspend fun findById(id: Long): Response<KutuphaneRes> {
        return kutuphaneService.findById(id)
    }

    override suspend fun findAll(): Response<List<KutuphaneRes>> {
        return kutuphaneService.findAll()
    }
}