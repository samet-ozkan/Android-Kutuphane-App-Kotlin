package com.sametozkan.kutuphane.domain.repository

import com.sametozkan.kutuphane.data.dto.request.KitapReq
import retrofit2.Response

interface GoogleBooksRepository {
    suspend fun fetchByIsbn(isbn: Long): Response<KitapReq>
}