package com.sametozkan.kutuphane.data.dto.request

data class KitapReq(
    val isbn: Long,
    val adi: String,
    val yayinYili: Int,
    val aciklama: String,
    val chatgptPuani: Double?,
    val chatgptYorumu: String?,
    val otomatikOlusturuldu: Boolean
)
