package com.eyehail.readingdataastrology

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.eyehail.readingdataastrology.Api.TheAstrologyApiService
import com.eyehail.readingdataastrology.Model.SignData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class MainActivity : AppCompatActivity() {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://ohmanda.com/api/horoscope/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
    private val theAstrologyApiService by lazy {
        retrofit.create(TheAstrologyApiService::class.java)
    }
    private val serverResponse: TextView by lazy {
        findViewById(R.id.main_server_response)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getHoroscopeResponse()
    }
    private fun getHoroscopeResponse() {
        val call = theAstrologyApiService.returnHoroscope()
        call.enqueue(object: Callback<SignData> {
            override fun onFailure(call: Call<SignData>, t: Throwable) {
                Log.e("Main Activity", "Failed to get search results", t)
            }

            override fun onResponse(call: Call<SignData>, response: Response<SignData>) {
                if (response.isSuccessful) {
                    val dataResults = response.body()
                    val horoscope = dataResults?.horoscope ?: "No data"
                    serverResponse.text= horoscope
                    } else {
                        Log.e("Main Activity", "Failed to get search results \n" +
                                (response.errorBody()?.string() ?: "")
                        )
                }
            }
        })
    }
}