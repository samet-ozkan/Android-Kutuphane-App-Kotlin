package com.sametozkan.kutuphane.data.dto.response

data class KitapRes(
    val id: Long,
    val isbn: Long,
    val adi: String,
    val yayinYili: Int,
    val aciklama: String,
    val chatgptPuani: Double?,
    val chatgptYorumu: String?,
    val yazarlar: List<YazarRes>,
    val turler: List<TurRes>,
    val kutuphaneler: List<KutuphaneRes>,
    val kullanicilar: List<KullaniciRes>,
    val otomatikOlusturuldu: Boolean
)
