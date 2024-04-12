package com.sametozkan.kutuphane.data.datasource.remote

import com.sametozkan.kutuphane.data.dto.request.KitapYazarReq
import com.sametozkan.kutuphane.data.dto.response.KitapYazarRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface KitapYazarService {

    @POST("/api/kitap-yazar")
    suspend fun save(@Body kitapYazarReq: KitapYazarReq): Response<Unit>

    @PUT("/api/kitap-yazar/{id}")
    suspend fun update(@Path("id") id: Long, @Body kitapYazarReq: KitapYazarReq): Response<KitapYazarRes>

    @GET("/api/kitap-yazar")
    suspend fun findAll(): Response<List<KitapYazarRes>>

    @GET("/api/kitap-yazar/{id}")
    suspend fun findById(@Path("id") id: Long): Response<KitapYazarRes>
}