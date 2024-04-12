package com.sametozkan.kutuphane.data.dto.response

data class KitapYazarRes(
    val id: Long,
    val kitap: KitapRes,
    val yazar: YazarRes
)
