package com.sametozkan.kutuphane.data.dto.request

data class KitapKullaniciReq(
    val kitapId: Long,
    val kullaniciId: Long,
    val kutuphaneId: Long,
    val iadeDurumu: Boolean?
)
