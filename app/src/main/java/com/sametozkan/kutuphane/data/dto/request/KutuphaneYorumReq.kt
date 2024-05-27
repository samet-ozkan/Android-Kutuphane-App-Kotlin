package com.sametozkan.kutuphane.data.dto.request

data class KutuphaneYorumReq(
    val yorum: String,
    val kullanici_id: Long,
    val kutuphane_id: Long
)
