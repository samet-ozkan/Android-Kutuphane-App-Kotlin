package com.sametozkan.kutuphane.data.dto.request

data class KitapReq(
    val isbn: Long,
    val adi: String,
    val yayinTarihi: String,
    val dil: String,
    val sayfaSayisi: Int,
    val aciklama: String,

)
