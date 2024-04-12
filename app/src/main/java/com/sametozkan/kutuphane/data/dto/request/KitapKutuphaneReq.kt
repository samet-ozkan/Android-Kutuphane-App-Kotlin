package com.sametozkan.kutuphane.data.dto.request

data class KitapKutuphaneReq(
    val kitap_id: Long,
    val kutuphane_id: Long,
    val stok: Int
)