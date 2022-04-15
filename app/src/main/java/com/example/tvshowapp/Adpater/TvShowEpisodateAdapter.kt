package com.example.tvshowapp.Adpater

import android.content.Context
import android.util.Log

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast

import androidx.recyclerview.widget.RecyclerView
import com.example.tvshowapp.Model.TvShowFavorite
import com.example.tvshowapp.Model.TvShows
import com.example.tvshowapp.R
import com.example.tvshowapp.ViewModel.TvShowEpisodateViewModel
import com.example.tvshowapp.ViewModel.TvShowFavoriteViewModel
import com.example.tvshowapp.databinding.TvshowItemsBinding
import com.squareup.picasso.Picasso


class TvShowEpisodateAdapter(
    val tvShow: List<TvShows>,
    val idUser: String,
    val context:Context
):RecyclerView.Adapter<TvShowEpisodateAdapter.ViewHolder>() {

    //instance TvShowFavoriteViewModel for created favorite
    val service=TvShowFavoriteViewModel()

    public class ViewHolder(binding: TvshowItemsBinding ):RecyclerView.ViewHolder(binding.root){
        val binding=binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TvshowItemsBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tvShow=tvShow[position]
        Picasso.get().load(tvShow.image)
            .resize(474, 702)
            .centerCrop()
            .placeholder(R.drawable.poster_placeholder)
            .into(holder.binding.postTvShow)

        holder.binding.tvShowName.text=tvShow.name
        holder.binding.tvShowStatus.text=tvShow.status
        holder.binding.tvShowNetwork.text=tvShow.network
        holder.binding.tvShowDateStart.text=tvShow.start_date

        //for get the tvShow selected and add a tv show favorite
        holder.binding.buttonAddFavTvShow.setOnClickListener(){
            val tvShowFavorite=TvShowFavorite(
                idUser = idUser,
                idTvShow =tvShow.id.toString(),
                idFavorites=null
            )
            //call a fun the add favorite
            var name =tvShow.name
           addFavorite(tvShowFavorite,name)


        }

    }

    override fun getItemCount(): Int {
        return tvShow.size
    }

    //for call the fun what save favorite
    fun addFavorite(tvShowFavorite: TvShowFavorite, nameTvShow:String){
        service.addTvShowFavorite(tvShowFavorite){ tvShow->
            if(tvShow?.idFavorites !=null){
                Toast.makeText(context, "add favorite $nameTvShow", Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(context, "Failed favorite", Toast.LENGTH_SHORT).show()


            }

        }
    }


}