package com.example.tvshowapp.Model

import com.example.tvshowapp.services.servicesFavoriteTvShow
import com.google.gson.annotations.SerializedName

class TvShowFavorite(
    var idUser:String?,
    var idTvShow: String?,
    var idFavorites:Int?
)

class TvShowFavoriteRespond {
    var idFavorites:Int=0
    var idUser:String=""
    var idTvShow: String=""
}
