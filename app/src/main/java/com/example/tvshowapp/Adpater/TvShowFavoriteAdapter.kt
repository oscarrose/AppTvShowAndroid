package com.example.tvshowapp.Adpater

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.tvshowapp.Model.TvShowFavorite
import com.example.tvshowapp.Model.TvShows
import com.example.tvshowapp.R
import com.example.tvshowapp.ViewModel.TvShowFavoriteViewModel
import com.example.tvshowapp.databinding.TvshowFavoriteItemsBinding
import com.example.tvshowapp.fragment.ListFavoritesFragment
import com.google.protobuf.Empty
import com.squareup.picasso.Picasso
import java.lang.reflect.Method

class TvShowFavoriteAdapter(
    val tvShowFavorite: List<TvShows>,
    val idUser: String,
    val context: Context

    ):RecyclerView.Adapter<TvShowFavoriteAdapter.ViewHolder>(){

    //get instance
    val service=TvShowFavoriteViewModel()


    public class ViewHolder(binding:TvshowFavoriteItemsBinding):RecyclerView.ViewHolder(binding.root){
        val binding=binding
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TvshowFavoriteItemsBinding.inflate(LayoutInflater.from(parent.context)))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tvFavorite=tvShowFavorite[position]

        holder.binding.tvShowName.text= tvFavorite.name
        holder.binding.tvShowStatus.text=tvFavorite.status
        holder.binding.tvShowCountry.text=tvFavorite.country
        holder.binding.tvShowNetwork.text=tvFavorite.network
        holder.binding.tvShowDateStart.text=tvFavorite.start_date

        Picasso.get().load(tvFavorite.image)
            .resize(474, 702)
            .centerCrop()
            .placeholder(R.drawable.poster_placeholder)
            .into(holder.binding.postTvShow)


        holder.binding.buttonRmFavTvShow.setOnClickListener(){
             var idTvShow= tvFavorite.id.toString()
            var positionItems=holder.adapterPosition //position current the items

            var positionTvShow=getItemId(position).toInt() //get the id the element


            service.deleteTvShowFavorite(idTvShow,idUser){
                Toast.makeText(context,"tv show removed", Toast.LENGTH_SHORT).show()

               /*notifyItemRemoved(positionItems)
                notifyItemRangeChanged(positionItems,positionTvShow)*/


            }
        }
    }

    override fun getItemCount(): Int {
       return tvShowFavorite.size
    }


}