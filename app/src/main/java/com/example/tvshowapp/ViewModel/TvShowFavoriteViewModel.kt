package com.example.tvshowapp.ViewModel

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.tvshowapp.Model.TvShowFavorite
import com.example.tvshowapp.Model.TvShowFavoriteRespond
import com.example.tvshowapp.services.servicesFavoriteTvShow
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowFavoriteViewModel(): ViewModel() {

    private val _listTvShowFavorite: MutableLiveData<List<TvShowFavoriteRespond>> = MutableLiveData()
    val tvShowListFavorite: LiveData<List<TvShowFavoriteRespond>> =  _listTvShowFavorite;

    private val _error:MutableLiveData<String> = MutableLiveData()
    val error:LiveData<String> =_error;

    private val service=servicesFavoriteTvShow.getInstance()


    fun addTvShowFavorite(tvShowFavorite: TvShowFavorite, onResult:(TvShowFavorite?)-> Unit) {

       service.createdTvShowFavorite(tvShowFavorite).enqueue(
           object : Callback<TvShowFavorite> {
               override fun onResponse(
                   call: Call<TvShowFavorite>,
                   response: Response<TvShowFavorite>
               ) {
                   val createdTvShow = response.body()
                   onResult(createdTvShow)
               }

               override fun onFailure(call: Call<TvShowFavorite>, t: Throwable) {
                  _error.postValue(t.message)

               }
           })

    }

    fun loadTvShowFavorite(idUser:String){
       CoroutineScope(Dispatchers.IO).launch {
            service.getTvShowFavorite(idUser).enqueue(
                object :Callback<List<TvShowFavoriteRespond>>{
                    override fun onResponse(
                        call: Call<List<TvShowFavoriteRespond>>,
                        response: Response<List<TvShowFavoriteRespond>>
                    ) {
                       _listTvShowFavorite.postValue(response.body())
                    }

                    override fun onFailure(call: Call<List<TvShowFavoriteRespond>>, t: Throwable) {
                       _error.postValue(t.message)

                    }

                }
            )
        }

    }
   fun deleteTvShowFavorite(idTvshow:String, idUser: String, onResult: (List<TvShowFavoriteRespond>?) -> Unit){
       CoroutineScope(Dispatchers.IO).launch {
           service.deteleTvShowFavorite(idTvshow,idUser).enqueue(
               object :Callback<List<TvShowFavoriteRespond>>{
                   override fun onResponse(
                       call: Call<List<TvShowFavoriteRespond>>,
                       response: Response<List<TvShowFavoriteRespond>>
                   ) {
                       onResult(response.body())

                   }

                   override fun onFailure(call: Call<List<TvShowFavoriteRespond>>, t: Throwable) {
                       _error.postValue(t.message)
                   }

               }
           )
       }

    }

}






