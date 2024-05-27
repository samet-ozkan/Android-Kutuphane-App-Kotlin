package com.sametozkan.kutuphane.presentation.kullanici.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class KullaniciHomeViewModel @Inject constructor() : ViewModel() {

    val changeFragment = MutableLiveData<String>()
}