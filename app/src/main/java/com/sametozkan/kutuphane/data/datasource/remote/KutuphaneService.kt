package com.sametozkan.kutuphane.data.datasource.remote

import com.sametozkan.kutuphane.data.dto.request.KutuphaneReq
import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface KutuphaneService {

    @PUT("/api/kutuphane/{id}")
    suspend fun update(@Path("id") id: Long, @Body kutuphaneReq: KutuphaneReq): Response<KutuphaneRes>

    @GET("/api/kutuphane")
    suspend fun findAll(): Response<List<KutuphaneRes>>

    @GET("/api/kutuphane/{id}")
    suspend fun findById(@Path("id") id: Long): Response<KutuphaneRes>

    @GET("/api/kutuphane/account/{accountId}")
    suspend fun findByAccountId(@Path("accountId") accountId: Long) : Response<KutuphaneRes>
}