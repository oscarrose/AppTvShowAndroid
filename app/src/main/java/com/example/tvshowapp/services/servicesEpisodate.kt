package com.example.tvshowapp.services

import com.example.tvshowapp.Model.TvShowMostPopularRespond
import com.google.android.gms.common.config.GservicesValue
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface servicesEpisodate {

    @GET("api/most-popular")
   fun getTvShowMostPopular(@Query("page") page:Int=1): Call<TvShowMostPopularRespond>

    companion object{
        private var _instance:servicesEpisodate?=null
        fun getInstance():servicesEpisodate{
            if(_instance==null){
                val retrofit=Retrofit.Builder()
                    .baseUrl("https://www.episodate.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(OkHttpClient.Builder().build())
                    .build();

                _instance=retrofit.create(servicesEpisodate::class.java)
            }

            return _instance!!;
        }
    }


}