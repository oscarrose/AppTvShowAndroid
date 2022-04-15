package com.example.tvshowapp.Model

import com.google.gson.annotations.SerializedName

class TvShows {
    var id: Long = 0
    var name: String =""
    var start_date:String=""
    var end_date:String=""
    var country:String=""
    var network:String=""
    var status:String=""
    @SerializedName("image_thumbnail_path")
    var image:String=""
}

class TvShowMostPopularRespond{
    var total:Int=0
    var page:Int=0
    var pages:Int=0
    @SerializedName("tv_shows")
    var tvShows: List<TvShows>?=null

}