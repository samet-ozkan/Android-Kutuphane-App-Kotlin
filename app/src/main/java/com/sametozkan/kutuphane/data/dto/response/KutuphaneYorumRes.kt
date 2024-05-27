package com.sametozkan.kutuphane.data.dto.response

data class KutuphaneYorumRes(
    val id: Long,
    val yorum: String,
    val kullanici: KullaniciRes,
    val kutuphane: KutuphaneRes
)