package com.sametozkan.kutuphane

import android.app.Application
import android.content.Context
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.security.ProviderInstaller
import dagger.hilt.android.HiltAndroidApp
import java.security.NoSuchAlgorithmException
import javax.net.ssl.SSLContext

@HiltAndroidApp
class MyApplication : Application() {
}