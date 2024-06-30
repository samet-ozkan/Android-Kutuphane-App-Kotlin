package com.sametozkan.kutuphane.data.dto.response

import java.time.LocalDateTime

data class KitapKullaniciRes(
    val id: Long,
    val kitap: KitapRes,
    val kutuphane: KutuphaneRes,
    val kullanici: KullaniciRes,
    val alimTarihi: String?,
    val teslimTarihi: String?,
    val createdTime: String,
    val iadeDurumu: Boolean,
    val onaylandi: Boolean?
)
