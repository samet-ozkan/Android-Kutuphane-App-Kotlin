package com.sametozkan.kutuphane.data.datasource.remote

import com.sametozkan.kutuphane.data.dto.request.KitapReq
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GoogleBooksService {
    @GET("/api/kitap/fetch/{isbn}")
    suspend fun fetchByIsbn(@Path("isbn") isbn: Long): Response<KitapReq>
}