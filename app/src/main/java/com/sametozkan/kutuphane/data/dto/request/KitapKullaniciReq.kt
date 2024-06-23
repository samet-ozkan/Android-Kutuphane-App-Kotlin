package com.sametozkan.kutuphane.data.dto.request

data class KitapKullaniciReq(
    val kitapId: Long,
    val kullaniciAccountId: Long,
    val kutuphaneId: Long,
    val iadeDurumu: Boolean?
)
