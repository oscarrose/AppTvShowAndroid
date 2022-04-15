package com.example.tvshowapp.ViewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvshowapp.Model.TvShowMostPopularRespond
import com.example.tvshowapp.Model.TvShows
import com.example.tvshowapp.services.servicesEpisodate
import com.squareup.okhttp.Dispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowEpisodateViewModel():ViewModel() {

    private val _listTvShow:MutableLiveData<List<TvShows>> = MutableLiveData()
    val tvShowList: LiveData<List<TvShows>> = _listTvShow;

    private val _error:MutableLiveData<String> = MutableLiveData()
    val error:LiveData<String> =_error;

    fun loadTvShows(){
        var service=servicesEpisodate.getInstance()
       CoroutineScope(Dispatchers.IO).launch {
            service.getTvShowMostPopular().enqueue(object: Callback<TvShowMostPopularRespond> {
                override fun onResponse(
                    call: Call<TvShowMostPopularRespond>,
                    response: Response<TvShowMostPopularRespond>
                ) {
                   _listTvShow.postValue(response.body()!!.tvShows)
                }

                override fun onFailure(call: Call<TvShowMostPopularRespond>, t: Throwable) {
                    _error.postValue(t.message)
                }
            })
        }
    }

}