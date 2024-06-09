package com.sametozkan.kutuphane.data.dto.response

data class KitapYorumRes(
    val id: Long,
    val yorum: String,
    val spoiler: Boolean,
    val kullanici: KullaniciRes,
    val kitap: KitapRes
)