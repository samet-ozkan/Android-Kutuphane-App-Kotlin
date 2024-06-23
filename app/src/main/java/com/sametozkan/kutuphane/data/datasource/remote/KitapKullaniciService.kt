package com.sametozkan.kutuphane.data.datasource.remote

import com.sametozkan.kutuphane.data.dto.request.KitapKullaniciReq
import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import com.sametozkan.kutuphane.data.dto.response.KitapOnerisiRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface KitapKullaniciService {

    @POST("/api/kitap-kullanici")
    suspend fun save(@Body kitapKullaniciReq: KitapKullaniciReq): Response<Unit>

    @PUT("/api/kitap-kullanici/{id}")
    suspend fun update(@Path("id") id: Long, @Body kitapKullaniciReq: KitapKullaniciReq): Response<KitapKullaniciRes>

    @GET("/api/kitap-kullanici")
    suspend fun findAll(): Response<List<KitapKullaniciRes>>

    @GET("/api/kitap-kullanici/{id}")
    suspend fun findById(@Path("id") id: Long): Response<KitapKullaniciRes>

    @GET("/api/kitap-kullanici/onay-bekleyenler/{accountId}")
    suspend fun findByKullaniciIdAndOnaylandiIsNull(@Path("accountId") accountId: Long): Response<List<KitapKullaniciRes>>

    @GET("/api/kitap-kullanici/onaylandi/{accountId}")
    suspend fun findByKullaniciIdAndOnaylandiIsFalse(@Path("accountId") accountId: Long): Response<List<KitapKullaniciRes>>

    @GET("/api/kitap-kullanici/reddedildi/{accountId}")
    suspend fun findByKullaniciIdAndOnaylandiIsTrue(@Path("accountId") accountId: Long): Response<List<KitapKullaniciRes>>

    @GET("/api/kitap-kullanici/kutuphane/{accountId}")
    suspend fun findByKutuphaneId(@Path("accountId") accountId: Long): Response<List<KitapKullaniciRes>>

    @GET("/api/kitap-kullanici/kitap-onerileri/{accountId}")
    suspend fun fetchKitapOnerileri(@Path("accountId") accountId: Long): Response<List<KitapOnerisiRes>>

    @POST("/api/kitap-kullanici/onayla/{kitapKullaniciId}")
    suspend fun kitapIstegiOnayla(@Path("kitapKullaniciId") kitapKullaniciId : Long): Response<Unit>

    @POST("/api/kitap-kullanici/reddet/{kitapKullaniciId}")
    suspend fun kitapIstegiReddet(@Path("kitapKullaniciId") kitapKullaniciId: Long): Response<Unit>

    @GET("/api/kitap-kullanici/teslim-edilmeyenler/{kutuphaneAccountId}")
    suspend fun teslimEdilmeyenler(@Path("kutuphaneAccountId") kutuphaneAccountId: Long): Response<List<KitapKullaniciRes>>

    @POST("/api/kitap-kullanici/teslim-edildi/{kitapKullaniciId}")
    suspend fun teslimEdildi(@Path("kitapKullaniciId") kitapKullaniciId: Long): Response<Unit>

    @DELETE("/api/kitap-kullanici/delete/{id}")
    suspend fun deleteById(@Path("id") id: Long) : Response<Unit>
}