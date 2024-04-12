package com.sametozkan.kutuphane.data.dto.response

data class YazarRes(
    val id: Long,
    val adi: String,
    val soyadi: String,
    val kitaplar: List<KitapRes>
)
