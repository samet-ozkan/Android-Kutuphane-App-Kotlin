package com.sametozkan.kutuphane.data.dto.response

data class KullaniciRes(
    val id: Long,
    val adi: String,
    val soyadi: String,
    val email: String,
    val account: AccountRes,
    val kitaplar: List<KitapRes>
)
