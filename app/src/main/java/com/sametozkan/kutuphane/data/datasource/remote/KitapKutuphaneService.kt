package com.sametozkan.kutuphane.data.datasource.remote

import com.sametozkan.kutuphane.data.dto.request.KitapKutuphaneReq
import com.sametozkan.kutuphane.data.dto.response.KitapKutuphaneRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface KitapKutuphaneService {

    @POST("/api/kitap-kutuphane")
    suspend fun save(@Body kitapKutuphaneReq: KitapKutuphaneReq): Response<Unit>

    @PUT("/api/kitap-kutuphane/{id}")
    suspend fun update(@Path("id") id:Long, @Body kitapKutuphaneReq: KitapKutuphaneReq): Response<KitapKutuphaneRes>

    @GET("/api/kitap-kutuphane/{id}")
    suspend fun findById(@Path("id") id: Long): Response<KitapKutuphaneRes>

    @GET("/api/kitap-kutuphane")
    suspend fun findAll(): Response<List<KitapKutuphaneRes>>
}