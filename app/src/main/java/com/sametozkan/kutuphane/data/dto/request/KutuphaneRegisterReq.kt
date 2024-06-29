package com.sametozkan.kutuphane.data.dto.request

data class KutuphaneRegisterReq(
    val account: AccountReq,
    val kutuphane: KutuphaneReq,
    val dogrulamaKodu: String
)
