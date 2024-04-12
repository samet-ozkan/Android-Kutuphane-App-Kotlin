package com.sametozkan.kutuphane.data.repository

import com.sametozkan.kutuphane.data.datasource.remote.KitapKutuphaneService
import com.sametozkan.kutuphane.data.dto.request.KitapKutuphaneReq
import com.sametozkan.kutuphane.data.dto.response.KitapKutuphaneRes
import com.sametozkan.kutuphane.domain.repository.KitapKutuphaneRepository
import retrofit2.Response

class KitapKutuphaneRepositoryImpl(private val kitapKutuphaneService: KitapKutuphaneService) :
    KitapKutuphaneRepository {

    override suspend fun save(kitapKutuphaneReq: KitapKutuphaneReq): Response<Unit> {
        return kitapKutuphaneService.save(kitapKutuphaneReq)
    }

    override suspend fun update(id: Long, kitapKutuphaneReq: KitapKutuphaneReq): Response<KitapKutuphaneRes> {
        return kitapKutuphaneService.update(id, kitapKutuphaneReq)
    }

    override suspend fun findById(id: Long): Response<KitapKutuphaneRes> {
        return kitapKutuphaneService.findById(id)
    }

    override suspend fun findAll(): Response<List<KitapKutuphaneRes>> {
        return kitapKutuphaneService.findAll()
    }
}