package com.sametozkan.kutuphane.data.dto.response

import android.accounts.Account

data class KutuphaneRes(
    val id: Long,
    val adi: String,
    val adresi: String,
    val sehir: String,
    val telefonNumarasi: String,
    val account: AccountRes,
    val kitaplar: List<KitapRes>,
    val teslimSuresi: Int,
)
