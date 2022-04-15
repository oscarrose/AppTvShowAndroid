package com.example.tvshowapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tvshowapp.Adpater.TvShowFavoriteAdapter
import com.example.tvshowapp.Model.TvShows
import com.example.tvshowapp.ViewModel.TvShowEpisodateViewModel
import com.example.tvshowapp.ViewModel.TvShowFavoriteViewModel
import com.example.tvshowapp.databinding.FragmentListFavoritesBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ListFavoritesFragment : Fragment() {

    //declare an instance
    lateinit var binding: FragmentListFavoritesBinding
    lateinit var auth: FirebaseAuth
    var myFavorite:List<TvShows> = emptyList()
    var todoTvShow:List<TvShows> = emptyList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentListFavoritesBinding.inflate(inflater, container,false)


        //orientation and inflate the recyclearView
        binding.listFavoriteRecyclerView.layoutManager=GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)


        // Initialize Firebase Auth
        auth = Firebase.auth

        //call method loading tv show favorite
        gettvShowFavorite()

        //refresh the recyClerView--------------------

        //swipe refresh
        binding.swipeRefreshList.setOnRefreshListener {
           gettvShowFavorite()
            //pass the data to recyCleView


            binding.swipeRefreshList.isRefreshing=false
        }
        //end the code for refresh the recyClerView


        //end inflate
        return binding.root
    }


    //for get the id the user
    private fun userIdCurrent(): String {
        //get the userId the user
        val user = auth.currentUser

        return user!!.uid
    }


    fun gettvShowFavorite(){
        //instance the viewModel
        val viewModel=ViewModelProvider(requireActivity()).get(TvShowEpisodateViewModel::class.java)

        //for map the data tvShow
        viewModel.tvShowList.observe(viewLifecycleOwner) {
            //pass the data to list
            todoTvShow =it

        }

        //for pass the error tvShow
        viewModel.error.observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }

        //for get the tv show
        viewModel.loadTvShows()

        //instance viewModel
        val viewModelFavorite= ViewModelProvider(requireActivity()).get(TvShowFavoriteViewModel::class.java)

        // for observer viewModel TvShowFavorite response
        viewModelFavorite.tvShowListFavorite.observe(viewLifecycleOwner) { it ->
            if (it !== null) {
                myFavorite=  todoTvShow.filter { tvShows ->
                    it.any{it.idTvShow.toLong()==tvShows.id }
                }
            }
            val data=myFavorite
            //pass the data to recyCleView
            binding.listFavoriteRecyclerView.adapter= TvShowFavoriteAdapter(myFavorite,
                userIdCurrent(), requireContext());
        }

        // for observer viewModel TvShowFavorite error
        viewModelFavorite.error.observe(viewLifecycleOwner){

            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
        }

        //for get the tv show favorite
        viewModelFavorite.loadTvShowFavorite(userIdCurrent())

    }


}