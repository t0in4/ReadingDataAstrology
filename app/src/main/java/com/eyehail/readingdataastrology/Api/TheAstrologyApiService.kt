package com.eyehail.readingdataastrology.Api

import com.eyehail.readingdataastrology.Model.SignData
import retrofit2.Call
import retrofit2.http.GET

interface TheAstrologyApiService {
    @GET("sagittarius/")
    fun returnHoroscope(): Call<SignData>
}