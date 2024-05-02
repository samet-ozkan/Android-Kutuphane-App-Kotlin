package com.sametozkan.kutuphane.data.repository

import com.sametozkan.kutuphane.data.datasource.remote.KitapService
import com.sametozkan.kutuphane.data.dto.request.KitapReq
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.domain.repository.KitapRepository
import retrofit2.Response

class KitapRepositoryImpl(private val kitapService: KitapService) : KitapRepository {

    override suspend fun save(kitapReq: KitapReq): Response<Long> {
        return kitapService.save(kitapReq)
    }

    override suspend fun update(id: Long, kitapReq: KitapReq): Response<KitapRes> {
        return kitapService.update(id, kitapReq)
    }

    override suspend fun findById(id: Long): Response<KitapRes> {
        return kitapService.findById(id)
    }

    override suspend fun findAll(): Response<List<KitapRes>> {
        return kitapService.findAll()
    }

    override suspend fun findByIsbn(isbn: Long): Response<KitapRes> {
        return kitapService.findByIsbn(isbn)
    }

    override suspend fun fetchByIsbn(isbn: Long): Response<KitapReq>{
        return kitapService.fetchByIsbn(isbn)
    }
}