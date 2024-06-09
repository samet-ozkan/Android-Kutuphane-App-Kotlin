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
import com.sametozkan.kutuphane.data.repository.AccountRepositoryImpl
import com.sametozkan.kutuphane.data.repository.AuthRepositoryImpl
import com.sametozkan.kutuphane.data.repository.KitapKullaniciRepositoryImpl
import com.sametozkan.kutuphane.data.repository.KitapKutuphaneRepositoryImpl
import com.sametozkan.kutuphane.data.repository.KitapRepositoryImpl
import com.sametozkan.kutuphane.data.repository.KitapTurRepositoryImpl
import com.sametozkan.kutuphane.data.repository.KitapYazarRepositoryImpl
import com.sametozkan.kutuphane.data.repository.KitapYorumRepositoryImpl
import com.sametozkan.kutuphane.data.repository.KullaniciRepositoryImpl
import com.sametozkan.kutuphane.data.repository.KutuphaneRepositoryImpl
import com.sametozkan.kutuphane.data.repository.KutuphaneYorumRepositoryImpl
import com.sametozkan.kutuphane.data.repository.TurRepositoryImpl
import com.sametozkan.kutuphane.data.repository.YazarRepositoryImpl
import com.sametozkan.kutuphane.domain.repository.AccountRepository
import com.sametozkan.kutuphane.domain.repository.AuthRepository
import com.sametozkan.kutuphane.domain.repository.KitapKullaniciRepository
import com.sametozkan.kutuphane.domain.repository.KitapKutuphaneRepository
import com.sametozkan.kutuphane.domain.repository.KitapRepository
import com.sametozkan.kutuphane.domain.repository.KitapTurRepository
import com.sametozkan.kutuphane.domain.repository.KitapYazarRepository
import com.sametozkan.kutuphane.domain.repository.KitapYorumRepository
import com.sametozkan.kutuphane.domain.repository.KullaniciRepository
import com.sametozkan.kutuphane.domain.repository.KutuphaneRepository
import com.sametozkan.kutuphane.domain.repository.KutuphaneYorumRepository
import com.sametozkan.kutuphane.domain.repository.TurRepository
import com.sametozkan.kutuphane.domain.repository.YazarRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    //Repositories
    @Singleton
    @Provides
    fun provideAccountRepository(accountService: AccountService): AccountRepository =
        AccountRepositoryImpl(accountService)

    @Singleton
    @Provides
    fun provideAuthRepository(authService: AuthService): AuthRepository =
        AuthRepositoryImpl(authService)

    @Singleton
    @Provides
    fun provideKitapKullaniciRepository(kitapKullaniciService: KitapKullaniciService): KitapKullaniciRepository =
        KitapKullaniciRepositoryImpl(kitapKullaniciService)

    @Singleton
    @Provides
    fun provideKitapKutuphaneRepository(kitapKutuphaneService: KitapKutuphaneService): KitapKutuphaneRepository =
        KitapKutuphaneRepositoryImpl(kitapKutuphaneService)

    @Singleton
    @Provides
    fun provideKitapRepository(kitapService: KitapService): KitapRepository =
        KitapRepositoryImpl(kitapService)

    @Singleton
    @Provides
    fun provideKitapTurRepository(kitapTurService: KitapTurService): KitapTurRepository =
        KitapTurRepositoryImpl(kitapTurService)

    @Singleton
    @Provides
    fun provideKitapYazarRepository(kitapYazarService: KitapYazarService): KitapYazarRepository =
        KitapYazarRepositoryImpl(kitapYazarService)

    @Singleton
    @Provides
    fun provideKullaniciRepository(kullaniciService: KullaniciService): KullaniciRepository =
        KullaniciRepositoryImpl(kullaniciService)

    @Singleton
    @Provides
    fun provideKutuphaneRepository(kutuphaneService: KutuphaneService): KutuphaneRepository =
        KutuphaneRepositoryImpl(kutuphaneService)

    @Singleton
    @Provides
    fun provideKutuphaneYorumRepository(kutuphaneYorum: KutuphaneYorumService): KutuphaneYorumRepository =
        KutuphaneYorumRepositoryImpl(kutuphaneYorum)

    @Singleton
    @Provides
    fun provideKitapYorumRepository(kitapYorum: KitapYorumService): KitapYorumRepository =
        KitapYorumRepositoryImpl(kitapYorum)

    @Singleton
    @Provides
    fun provideTurRepository(turService: TurService): TurRepository =
        TurRepositoryImpl(turService)

    @Singleton
    @Provides
    fun provideYazarRepository(yazarService: YazarService): YazarRepository =
        YazarRepositoryImpl(yazarService)
}