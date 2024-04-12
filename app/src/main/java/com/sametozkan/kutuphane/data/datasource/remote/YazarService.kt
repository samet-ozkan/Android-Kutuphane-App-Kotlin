package com.sametozkan.kutuphane.data.datasource.remote

import com.sametozkan.kutuphane.data.dto.request.YazarReq
import com.sametozkan.kutuphane.data.dto.response.YazarRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface YazarService {

    @POST("/api/yazar")
    suspend fun save(@Body yazarReq: YazarReq): Response<Unit>

    @PUT("/api/yazar/{id}")
    suspend fun update(@Path("id") id: Long, @Body yazarReq: YazarReq): Response<YazarRes>

    @GET("/api/yazar")
    suspend fun findAll(): Response<List<YazarRes>>

    @GET("/api/yazar/{id}")
    suspend fun findById(@Path("id") id: Long): Response<YazarRes>
}