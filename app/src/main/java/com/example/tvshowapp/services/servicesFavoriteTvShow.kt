package com.example.tvshowapp.services

import com.example.tvshowapp.Model.TvShowFavorite
import com.example.tvshowapp.Model.TvShowFavoriteRespond
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface servicesFavoriteTvShow {

    //method post for save tvShowFavorite
    @Headers("Content-Type: application/json")
    @POST("/api/EntitesFavorites")
    fun createdTvShowFavorite(@Body tvShowFavorite: TvShowFavorite):Call<TvShowFavorite>

    //get tvShow favorite-- Request using @Path
    @GET("/api/EntitesFavorites/{userId}")
    fun getTvShowFavorite(@Path("userId") idUser:String ):Call<List<TvShowFavoriteRespond>>

    //for delete a tv Show the favorite
   @DELETE("/api/EntitesFavorites/{idTvShow}")
    fun deteleTvShowFavorite(@Path("idTvShow") idTvshow:String, @Query("idUser") idUser: String ):Call<List<TvShowFavoriteRespond>>


    //for have a only instance the api
    companion object{
        private var _instance:servicesFavoriteTvShow?=null
        fun getInstance():servicesFavoriteTvShow{
            if(_instance==null){
                var retrofit=Retrofit.Builder()
                    .baseUrl("https://apitvshowsv2.azurewebsites.net")
                   .addConverterFactory(GsonConverterFactory.create())
                    .client(OkHttpClient.Builder().build())
                    .build();

                _instance=retrofit.create(servicesFavoriteTvShow::class.java)
            }
            return _instance!!
        }
    }

}