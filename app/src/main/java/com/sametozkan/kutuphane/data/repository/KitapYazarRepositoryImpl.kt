package com.sametozkan.kutuphane.data.repository

import com.sametozkan.kutuphane.data.datasource.remote.KitapYazarService
import com.sametozkan.kutuphane.data.dto.request.KitapYazarReq
import com.sametozkan.kutuphane.data.dto.response.KitapYazarRes
import com.sametozkan.kutuphane.domain.repository.KitapYazarRepository
import retrofit2.Response

class KitapYazarRepositoryImpl(private val kitapYazarService: KitapYazarService) :
    KitapYazarRepository {

    override suspend fun save(kitapYazarReq: KitapYazarReq): Response<Unit> {
        return kitapYazarService.save(kitapYazarReq)
    }

    override suspend fun update(id: Long, kitapYazarReq: KitapYazarReq): Response<KitapYazarRes> {
        return kitapYazarService.update(id, kitapYazarReq)
    }

    override suspend fun findById(id: Long): Response<KitapYazarRes> {
        return kitapYazarService.findById(id)
    }

    override suspend fun findAll(): Response<List<KitapYazarRes>> {
        return kitapYazarService.findAll()
    }
}