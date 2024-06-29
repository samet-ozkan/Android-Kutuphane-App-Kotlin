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

    override suspend fun update(
        id: Long,
        kitapKullaniciReq: KitapKullaniciReq
    ): Response<KitapKullaniciRes> {
        return kitapKullaniciService.update(id, kitapKullaniciReq)
    }

    override suspend fun findAll(): Response<List<KitapKullaniciRes>> {
        return kitapKullaniciService.findAll()
    }

    override suspend fun findById(id: Long): Response<KitapKullaniciRes> {
        return kitapKullaniciService.findById(id)
    }

    override suspend fun findByKullaniciIdAndOnaylandiIsNull(accountId: Long): Response<List<KitapKullaniciRes>> {
        return kitapKullaniciService.findByKullaniciIdAndOnaylandiIsNull(accountId)
    }

    override suspend fun findByKullaniciIdAndOnaylandiIsFalse(accountId: Long): Response<List<KitapKullaniciRes>> {
        return kitapKullaniciService.findByKullaniciIdAndOnaylandiIsFalse(accountId)
    }

    override suspend fun findByKullaniciIdAndOnaylandiIsTrue(accountId: Long): Response<List<KitapKullaniciRes>> {
        return kitapKullaniciService.findByKullaniciIdAndOnaylandiIsTrue(accountId)
    }

    override suspend fun findByKutuphaneId(accountId: Long): Response<List<KitapKullaniciRes>> {
        return kitapKullaniciService.findByKutuphaneId(accountId)
    }


    override suspend fun kitapIstegiOnayla(kitapKullaniciId: Long): Response<Unit> {
        return kitapKullaniciService.kitapIstegiOnayla(kitapKullaniciId)
    }

    override suspend fun kitapIstegiReddet(kitapKullaniciId: Long): Response<Unit> {
        return kitapKullaniciService.kitapIstegiReddet(kitapKullaniciId)
    }

    override suspend fun teslimEdilmeyenler(kutuphaneAccountId: Long): Response<List<KitapKullaniciRes>> {
        return kitapKullaniciService.teslimEdilmeyenler(kutuphaneAccountId)
    }

    override suspend fun teslimEdildi(kitapKullaniciId: Long): Response<Unit> {
        return kitapKullaniciService.teslimEdildi(kitapKullaniciId)
    }

    override suspend fun deleteById(id: Long): Response<Unit> {
        return kitapKullaniciService.deleteById(id);
    }
}