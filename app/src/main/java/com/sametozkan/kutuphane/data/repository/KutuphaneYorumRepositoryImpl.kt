package com.sametozkan.kutuphane.data.repository

import com.sametozkan.kutuphane.data.datasource.remote.KutuphaneYorumService
import com.sametozkan.kutuphane.data.dto.request.KutuphaneYorumReq
import com.sametozkan.kutuphane.data.dto.response.KutuphaneYorumRes
import com.sametozkan.kutuphane.domain.repository.KutuphaneYorumRepository
import retrofit2.Response

class KutuphaneYorumRepositoryImpl(private val kutuphaneYorumService: KutuphaneYorumService) :
    KutuphaneYorumRepository {

    override suspend fun save(kutuphaneYorumReq: KutuphaneYorumReq): Response<Long> {
        return kutuphaneYorumService.save(kutuphaneYorumReq)
    }

    override suspend fun findByKutuphaneId(kutuphaneId: Long): Response<List<KutuphaneYorumRes>> {
        return kutuphaneYorumService.findByKutuphaneId(kutuphaneId)
    }
}