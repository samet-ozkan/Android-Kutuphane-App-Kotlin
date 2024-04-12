package com.sametozkan.kutuphane.data.datasource.remote

import com.sametozkan.kutuphane.data.dto.request.TurReq
import com.sametozkan.kutuphane.data.dto.response.TurRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TurService {

    @POST("/api/tur")
    suspend fun save(@Body turReq: TurReq): Response<Unit>

    @PUT("/api/tur/{id}")
    suspend fun update(@Path("id") id: Long, @Body turReq: TurReq): Response<TurRes>

    @GET("/api/tur")
    suspend fun findAll(): Response<List<TurRes>>

    @GET("/api/tur/{id}")
    suspend fun findById(@Path("id") id: Long): Response<TurRes>
}