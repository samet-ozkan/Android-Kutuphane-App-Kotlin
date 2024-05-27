package com.sametozkan.kutuphane.data.datasource.remote

import com.sametozkan.kutuphane.data.dto.request.KutuphaneYorumReq
import com.sametozkan.kutuphane.data.dto.response.KutuphaneYorumRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface KutuphaneYorumService {

    @POST("/api/kutuphane-yorum")
    suspend fun save(@Body kutuphaneYorumReq: KutuphaneYorumReq) : Response<Long>

    @GET("/api/kutuphane-yorum/{kutuphaneId}")
    suspend fun findByKutuphaneId(@Path("kutuphaneId") kutuphaneId: Long) : Response<List<KutuphaneYorumRes>>

}