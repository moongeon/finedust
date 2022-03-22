package com.mungeun.finedust.di

import com.mungeun.finedust.api.AirKoreaApiService
import com.mungeun.finedust.api.KakaoLocalApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NetWorkModule {

    @Singleton
    @Provides
    fun provideKakaoApiService() : KakaoLocalApiService {
        return KakaoLocalApiService.create()
    }

    @Singleton
    @Provides
    fun provideAirKoreaApiService() : AirKoreaApiService{
        return AirKoreaApiService.create()
    }




}