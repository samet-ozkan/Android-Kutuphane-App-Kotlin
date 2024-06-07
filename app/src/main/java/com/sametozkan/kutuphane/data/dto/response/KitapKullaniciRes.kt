package com.sametozkan.kutuphane.data.dto.response

import java.time.LocalDateTime

data class KitapKullaniciRes(
    val id: Long,
    val kitap: KitapRes,
    val kutuphane: KutuphaneRes,
    val kullanici: KullaniciRes,
    val alimTarihi: List<Int>?,
    val teslimTarihi: List<Int>?,
    val createdTime: List<Int>,
    val iadeDurumu: Boolean,
    val onaylandi: Boolean?
    //Burda kaldim createdTime object bekliyorsun liste geliyor diyor.
)
