package com.sametozkan.kutuphane.data.datasource.remote

import com.sametozkan.kutuphane.data.dto.request.KitapReq
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface KitapService {

    @POST("/api/kitap")
    suspend fun save(@Body kitapReq: KitapReq): Response<Long>

    @PUT("/api/kitap/{id}")
    suspend fun update(@Path("id") id:Long, @Body kitapReq: KitapReq): Response<KitapRes>

    @GET("/api/kitap")
    suspend fun findAll(): Response<List<KitapRes>>

    @GET("/api/kitap/{id}")
    suspend fun findById(@Path("id") id: Long): Response<KitapRes>

    @GET("/api/kitap/isbn/{isbn}")
    suspend fun findByIsbn(@Path("isbn") isbn: Long): Response<KitapRes>

}