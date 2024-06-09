package com.sametozkan.kutuphane.data.dto.request

data class KitapYorumReq(
    val yorum: String,
    val kullanici_id: Long,
    val kitap_id: Long
)