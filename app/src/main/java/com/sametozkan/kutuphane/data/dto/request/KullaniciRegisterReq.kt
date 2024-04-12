package com.sametozkan.kutuphane.data.dto.request

import android.accounts.Account

data class KullaniciRegisterReq(
    val account: AccountReq,
    val kullanici: KullaniciReq
)
