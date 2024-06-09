package com.sametozkan.kutuphane.data.repository

import com.sametozkan.kutuphane.data.datasource.remote.KitapYorumService
import com.sametozkan.kutuphane.data.dto.request.KitapYorumReq
import com.sametozkan.kutuphane.data.dto.response.KitapYorumRes
import com.sametozkan.kutuphane.data.dto.response.KutuphaneYorumRes
import com.sametozkan.kutuphane.domain.repository.KitapYorumRepository
import retrofit2.Response

class KitapYorumRepositoryImpl(private val kitapYorumService: KitapYorumService) :
    KitapYorumRepository {

    override suspend fun save(kitapYorumReq: KitapYorumReq): Response<Long> {
        return kitapYorumService.save(kitapYorumReq)
    }

    override suspend fun findByKitapId(kitapId: Long): Response<List<KitapYorumRes>> {
        return kitapYorumService.findByKitapId(kitapId)
    }
}