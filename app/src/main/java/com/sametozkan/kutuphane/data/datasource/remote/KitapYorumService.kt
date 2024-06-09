package com.sametozkan.kutuphane.data.datasource.remote

import com.sametozkan.kutuphane.data.dto.request.KitapYorumReq
import com.sametozkan.kutuphane.data.dto.request.KutuphaneYorumReq
import com.sametozkan.kutuphane.data.dto.response.KitapYorumRes
import com.sametozkan.kutuphane.data.dto.response.KutuphaneYorumRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface KitapYorumService {

    @POST("/api/kitap-yorum")
    suspend fun save(@Body kitapYorumReq: KitapYorumReq) : Response<Long>

    @GET("/api/kitap-yorum/{kitapId}")
    suspend fun findByKitapId(@Path("kitapId") kitapId: Long) : Response<List<KitapYorumRes>>

}