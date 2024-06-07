package com.sametozkan.kutuphane.data.repository

import com.sametozkan.kutuphane.data.datasource.remote.KitapKullaniciService
import com.sametozkan.kutuphane.data.dto.request.KitapKullaniciReq
import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import com.sametozkan.kutuphane.domain.repository.KitapKullaniciRepository
import retrofit2.Response

class KitapKullaniciRepositoryImpl(private val kitapKullaniciService: KitapKullaniciService) :
    KitapKullaniciRepository {

    override suspend fun save(kitapKullaniciReq: KitapKullaniciReq): Response<Unit> {
        return kitapKullaniciService.save(kitapKullaniciReq)
    }

    override suspend fun update(id: Long, kitapKullaniciReq: KitapKullaniciReq): Response<KitapKullaniciRes> {
        return kitapKullaniciService.update(id, kitapKullaniciReq)
    }

    override suspend fun findAll(): Response<List<KitapKullaniciRes>> {
        return kitapKullaniciService.findAll()
    }

    override suspend fun findById(id: Long): Response<KitapKullaniciRes> {
        return kitapKullaniciService.findById(id)
    }

    override suspend fun findByKullaniciIdAndIadeDurumuIsNull(accountId: Long): Response<List<KitapKullaniciRes>> {
        return kitapKullaniciService.findByKullaniciIdAndIadeDurumuIsNull(accountId)
    }

    override suspend fun findByKullaniciIdAndIadeDurumuIsFalse(accountId: Long): Response<List<KitapKullaniciRes>> {
        return kitapKullaniciService.findByKullaniciIdAndIadeDurumuIsFalse(accountId)
    }

    override suspend fun findByKullaniciIdAndIadeDurumuIsTrue(accountId: Long): Response<List<KitapKullaniciRes>> {
        return kitapKullaniciService.findByKullaniciIdAndIadeDurumuIsTrue(accountId)
    }

    override suspend fun deleteById(id: Long) : Response<Unit>{
        return kitapKullaniciService.deleteById(id);
    }
}