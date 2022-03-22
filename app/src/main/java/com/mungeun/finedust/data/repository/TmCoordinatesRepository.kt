package com.mungeun.finedust.data.repository

import com.mungeun.finedust.data.model.TmCoordinates.Document

interface TmCoordinatesRepository {
    suspend fun getTmCoordinates(longitude: Double, latitude: Double) : Document
}