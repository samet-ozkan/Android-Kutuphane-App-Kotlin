package com.sametozkan.kutuphane.data.repository

import com.sametozkan.kutuphane.data.datasource.remote.KullaniciService
import com.sametozkan.kutuphane.data.dto.request.KullaniciReq
import com.sametozkan.kutuphane.data.dto.response.KullaniciRes
import com.sametozkan.kutuphane.domain.repository.KullaniciRepository
import retrofit2.Response

class KullaniciRepositoryImpl(private val kullaniciService: KullaniciService) :
    KullaniciRepository {

    override suspend fun update(id: Long, kullaniciReq: KullaniciReq): Response<KullaniciRes> {
        return kullaniciService.update(id, kullaniciReq)
    }

    override suspend fun findById(id: Long): Response<KullaniciRes> {
        return kullaniciService.findById(id)
    }

    override suspend fun findAll(): Response<List<KullaniciRes>> {
        return kullaniciService.findAll()
    }
}