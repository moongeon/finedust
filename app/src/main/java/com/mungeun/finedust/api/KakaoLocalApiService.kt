package com.mungeun.finedust.api

import com.mungeun.finedust.BuildConfig
import com.mungeun.finedust.data.model.TmCoordinates.TmCoordinatesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.Retrofit
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

interface KakaoLocalApiService {

    @Headers("Authorization: KakaoAK ${BuildConfig.KAKAO_API_KEY}")
    @GET("v2/local/geo/transcoord.json?output_coord=TM")
    suspend fun getTmCoordinates(
        @Query("x") longitude : Double,
        @Query("y") latitude : Double
    ) : Response<TmCoordinatesResponse>


    companion object{
        private const val KAKAO_BASE_URL = "https://dapi.kakao.com/"

        fun create() : KakaoLocalApiService {
            val logger = HttpLoggingInterceptor().apply {
                level =
                    HttpLoggingInterceptor.Level.BASIC
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(KAKAO_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(KakaoLocalApiService::class.java)
        }


    }

}