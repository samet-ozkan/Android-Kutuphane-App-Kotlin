package com.sametozkan.kutuphane.data.repository

import com.sametozkan.kutuphane.data.datasource.remote.KitapTurService
import com.sametozkan.kutuphane.data.dto.request.KitapTurReq
import com.sametozkan.kutuphane.data.dto.response.KitapTurRes
import com.sametozkan.kutuphane.domain.repository.KitapTurRepository
import retrofit2.Response

class KitapTurRepositoryImpl(private val kitapTurService: KitapTurService) : KitapTurRepository {

    override suspend fun save(kitapTurReq: KitapTurReq): Response<Unit> {
        return kitapTurService.save(kitapTurReq)
    }

    override suspend fun update(id: Long, kitapTurReq: KitapTurReq): Response<KitapTurRes> {
        return kitapTurService.update(id, kitapTurReq)
    }

    override suspend fun findById(id: Long): Response<KitapTurRes> {
        return kitapTurService.findById(id)
    }

    override suspend fun findAll(): Response<List<KitapTurRes>> {
        return kitapTurService.findAll()
    }
}