package com.sametozkan.kutuphane.data.dto.response

data class KitapKutuphaneRes(
    val id: Long,
    val kitap: KitapRes,
    val kutuphane: KutuphaneRes,
)
