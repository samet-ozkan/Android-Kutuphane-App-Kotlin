package com.sametozkan.kutuphane.data.datasource.local.sharedpreferences

interface Session {
    fun getJWT(): String?
    fun setJWT(jwt: String)
    fun getAccountID(): Long?
    fun setAccountID(id: Long)
    fun getAccountType(): String?
    fun setAccountType(type: String)
    fun clear()
}