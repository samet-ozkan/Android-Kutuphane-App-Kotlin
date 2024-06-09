package com.sametozkan.kutuphane.di

import com.sametozkan.kutuphane.data.datasource.remote.AccountService
import com.sametozkan.kutuphane.data.datasource.remote.AuthService
import com.sametozkan.kutuphane.data.datasource.remote.KitapKullaniciService
import com.sametozkan.kutuphane.data.datasource.remote.KitapKutuphaneService
import com.sametozkan.kutuphane.data.datasource.remote.KitapService
import com.sametozkan.kutuphane.data.datasource.remote.KitapTurService
import com.sametozkan.kutuphane.data.datasource.remote.KitapYazarService
import com.sametozkan.kutuphane.data.datasource.remote.KitapYorumService
import com.sametozkan.kutuphane.data.datasource.remote.KullaniciService
import com.sametozkan.kutuphane.data.datasource.remote.KutuphaneService
import com.sametozkan.kutuphane.data.datasource.remote.KutuphaneYorumService
import com.sametozkan.kutuphane.data.datasource.remote.TurService
import com.sametozkan.kutuphane.data.datasource.remote.YazarService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    //Services
    @Singleton
    @Provides
    fun provideAccountService(retrofit: Retrofit): AccountService =
        retrofit.create(AccountService::class.java)

    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Singleton
    @Provides
    fun provideKitapKullaniciService(retrofit: Retrofit): KitapKullaniciService =
        retrofit.create(KitapKullaniciService::class.java)

    @Singleton
    @Provides
    fun provideKitapKutuphaneService(retrofit: Retrofit): KitapKutuphaneService =
        retrofit.create(KitapKutuphaneService::class.java)

    @Singleton
    @Provides
    fun provideKitapService(retrofit: Retrofit): KitapService =
        retrofit.create(KitapService::class.java)

    @Singleton
    @Provides
    fun provideKitapTurService(retrofit: Retrofit): KitapTurService =
        retrofit.create(KitapTurService::class.java)

    @Singleton
    @Provides
    fun provideKitapYazarService(retrofit: Retrofit): KitapYazarService =
        retrofit.create(KitapYazarService::class.java)

    @Singleton
    @Provides
    fun provideKullaniciService(retrofit: Retrofit): KullaniciService =
        retrofit.create(KullaniciService::class.java)

    @Singleton
    @Provides
    fun provideKutuphaneService(retrofit: Retrofit): KutuphaneService =
        retrofit.create(KutuphaneService::class.java)

    @Singleton
    @Provides
    fun provideKutuphaneYorumService(retrofit: Retrofit): KutuphaneYorumService =
        retrofit.create(KutuphaneYorumService::class.java)

    @Singleton
    @Provides
    fun provideKitapYorumService(retrofit: Retrofit): KitapYorumService =
        retrofit.create(KitapYorumService::class.java)

    @Singleton
    @Provides
    fun provideTurService(retrofit: Retrofit): TurService = retrofit.create(TurService::class.java)

    @Singleton
    @Provides
    fun provideYazarService(retrofit: Retrofit): YazarService =
        retrofit.create(YazarService::class.java)
}