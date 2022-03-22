package com.mungeun.finedust.di

import com.mungeun.finedust.api.KakaoLocalApiService
import com.mungeun.finedust.api.AirKoreaApiService
import com.mungeun.finedust.data.repository.TmCoordinatesRepository
import com.mungeun.finedust.data.repository.TmCoordinatesRepositoryImpl
import com.mungeun.finedust.data.repository.MonitoringStationRepository
import com.mungeun.finedust.data.repository.MonitoringStationRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideTmCoordinatesRepository(kakaoLocalApiService:KakaoLocalApiService) : TmCoordinatesRepository {
        return TmCoordinatesRepositoryImpl(kakaoLocalApiService)
    }

    @Singleton
    @Provides
    fun provideMonitoringStationRepository(airKoreaApiService:AirKoreaApiService) : MonitoringStationRepository {
        return MonitoringStationRepositoryImpl(airKoreaApiService)

    }

}