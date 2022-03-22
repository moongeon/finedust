package com.mungeun.finedust.data.repository

import com.mungeun.finedust.api.AirKoreaApiService
import com.mungeun.finedust.data.model.airquality.Item
import com.mungeun.finedust.data.model.monitoringstation.MonitoringStation
import javax.inject.Inject

class MonitoringStationRepositoryImpl @Inject constructor
    (private val airKoreaApiService: AirKoreaApiService)
    : MonitoringStationRepository {
    override suspend fun getMonitoringStation(
        tmX: Double, tmY: Double,
    ): MonitoringStation? {
        return airKoreaApiService
            .getNearbyMonitoringStation(tmX!!, tmY!!)
            .body()
            ?.response
            ?.body
            ?.monitoringStations
            ?.minByOrNull { it.tm ?: Double.MAX_VALUE }
    }

    override suspend fun getItems(stationName: String): Item? {
        return airKoreaApiService.getRealtimeAirQualties(stationName)
            .body()
            ?.response
            ?.body
            ?.items
            ?.firstOrNull()
    }
}