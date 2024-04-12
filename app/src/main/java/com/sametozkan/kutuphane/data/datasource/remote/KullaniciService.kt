package com.sametozkan.kutuphane.data.datasource.remote

import com.sametozkan.kutuphane.data.dto.request.KullaniciReq
import com.sametozkan.kutuphane.data.dto.response.KullaniciRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface KullaniciService {

    @PUT("/api/kullanici/{id}")
    suspend fun update(@Path("id") id: Long, @Body kullaniciReq: KullaniciReq): Response<KullaniciRes>

    @GET("/api/kullanici")
    suspend fun findAll(): Response<List<KullaniciRes>>

    @GET("/api/kullanici/{id}")
    suspend fun findById(@Path("id") id: Long): Response<KullaniciRes>
}