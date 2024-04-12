package com.sametozkan.kutuphane.data.datasource.remote

import com.sametozkan.kutuphane.data.dto.request.KitapTurReq
import com.sametozkan.kutuphane.data.dto.response.KitapTurRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface KitapTurService {

    @POST("/api/kitap-tur")
    suspend fun save(@Body kitapTurReq: KitapTurReq): Response<Unit>

    @PUT("/api/kitap-tur/{id}")
    suspend fun update(@Path("id") id: Long, @Body kitapTurReq: KitapTurReq): Response<KitapTurRes>

    @GET("/api/kitap-tur")
    suspend fun findAll(): Response<List<KitapTurRes>>

    @GET("/api/kitap-tur/{id}")
    suspend fun findById(@Path("id") id: Long): Response<KitapTurRes>
}