package com.mungeun.finedust.data.repository

import com.mungeun.finedust.data.model.airquality.Item
import com.mungeun.finedust.data.model.monitoringstation.MonitoringStation


interface MonitoringStationRepository {
    suspend fun getMonitoringStation(tmX : Double, tmY : Double) : MonitoringStation?
    suspend fun getItems(stationName : String): Item?
}