package com.sametozkan.kutuphane.data.datasource.remote

import com.sametozkan.kutuphane.data.dto.request.KitapKullaniciReq
import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import retrofit2.Response
import retrofit2.http.Body
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
}