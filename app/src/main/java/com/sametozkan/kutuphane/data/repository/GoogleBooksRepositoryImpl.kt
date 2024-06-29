package com.sametozkan.kutuphane.data.repository

import com.sametozkan.kutuphane.data.datasource.remote.GoogleBooksService
import com.sametozkan.kutuphane.data.dto.request.KitapReq
import com.sametozkan.kutuphane.domain.repository.GoogleBooksRepository
import retrofit2.Response

class GoogleBooksRepositoryImpl(private val googleBooksService: GoogleBooksService) :
    GoogleBooksRepository {

    override suspend fun fetchByIsbn(isbn: Long): Response<KitapReq> {
        return googleBooksService.fetchByIsbn(isbn)
    }
}