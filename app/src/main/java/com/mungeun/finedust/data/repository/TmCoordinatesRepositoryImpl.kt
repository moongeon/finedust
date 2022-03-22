package com.mungeun.finedust.data.repository

import com.mungeun.finedust.api.KakaoLocalApiService
import com.mungeun.finedust.data.model.TmCoordinates.Document
import javax.inject.Inject

class TmCoordinatesRepositoryImpl @Inject constructor
    (private val kakaoLocalApiService : KakaoLocalApiService)
    : TmCoordinatesRepository {
    override suspend fun getTmCoordinates(
        longitude: Double, latitude: Double) : Document {
       val tmCoordinates =  kakaoLocalApiService
           .getTmCoordinates(longitude,latitude)
           .body()
           ?.documents
           ?.firstOrNull()

        val tmX = tmCoordinates!!.x
        val tmY = tmCoordinates!!.y

        return Document(tmX,tmY)





    }
}