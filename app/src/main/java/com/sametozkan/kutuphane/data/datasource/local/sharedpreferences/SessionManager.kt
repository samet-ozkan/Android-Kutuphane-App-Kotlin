package com.sametozkan.kutuphane.data.datasource.local.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SessionManager @Inject constructor(@ApplicationContext context: Context) : Session {
    companion object {
        val SP_NAME = "Session"
        val JWT = "JWT"
        val ACCOUNT_ID = "Account ID"
        val ACCOUNT_TYPE = "Account Type"
    }

    private val sharedPreferences: SharedPreferences
    private val editor: SharedPreferences.Editor

    init {
        sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        editor = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit()
    }

    override fun getJWT(): String? {
        return sharedPreferences.getString(JWT, "")
    }

    override fun setJWT(jwt: String) {
        editor.apply {
            putString(JWT, jwt)
            apply()
        }
    }

    override fun getAccountID(): Long? {
        return sharedPreferences.getLong(ACCOUNT_ID, 0)
    }

    override fun setAccountID(id: Long) {
        editor.apply {
            putLong(ACCOUNT_ID, id)
            apply()
        }
    }

    override fun getAccountType(): String? {
        return sharedPreferences.getString(ACCOUNT_TYPE, "")
    }

    override fun setAccountType(type: String) {
        editor.apply {
            putString(ACCOUNT_TYPE, type)
            apply()
        }
    }

    override fun clear() {
        editor.apply {
            clear()
            apply()
        }
    }

}
