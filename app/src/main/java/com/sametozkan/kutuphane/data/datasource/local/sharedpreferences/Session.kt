package com.sametozkan.kutuphane.data.datasource.local.sharedpreferences

import java.time.Instant

interface Session {
    fun getJWT(): String?
    fun setJWT(jwt: String)
    fun getRefreshToken(): String?
    fun setRefreshToken(refreshToken: String)
    fun getAccountID(): Long?
    fun setAccountID(id: Long)
    fun getAccountType(): String?
    fun setAccountType(type: String)
    fun clear()
    fun getRefreshExpiryDate(): Long?
    fun setRefreshExpiryDate(refreshExpiryDate: Long)
}