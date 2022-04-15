package com.example.tvshowapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tvshowapp.Adpater.TvShowEpisodateAdapter
import com.example.tvshowapp.Model.TvShowFavorite
import com.example.tvshowapp.Model.TvShowFavoriteRespond
import com.example.tvshowapp.ViewModel.TvShowEpisodateViewModel
import com.example.tvshowapp.ViewModel.TvShowFavoriteViewModel
import com.example.tvshowapp.databinding.FragmentListTvShowsBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ListTvShowsFragment : Fragment() {
    lateinit var binding: FragmentListTvShowsBinding
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentListTvShowsBinding.inflate(inflater, container, false)

        //orientation and inflate the recyclearView
        binding.listTvShowRecyClerView.layoutManager=GridLayoutManager(requireContext(),2)

        // Initialize Firebase Auth
        auth = Firebase.auth

        //call fun init
        initAppTvShow()
        //Toast.makeText(requireActivity(), userIdCurrent(), Toast.LENGTH_LONG).show()


        //end inflate
        return   binding.root

    }

    //for get the id the user
    fun userIdCurrent(): String {
        //get the userId the user
        val user = auth.currentUser

        return user!!.uid
    }

    private fun initAppTvShow(){
        //instance the viewModel
        val viewModel=ViewModelProvider(requireActivity()).get(TvShowEpisodateViewModel::class.java)

        //for map the data tvShow
        viewModel.tvShowList.observe(viewLifecycleOwner) {
            //get the userId the user
            val user = auth.currentUser
            val idUser= user!!.uid

            //pass the data to recyCleView
            binding.listTvShowRecyClerView.adapter=TvShowEpisodateAdapter(it, idUser, requireActivity());

        }

        //for pass the error tvShow
        viewModel.error.observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
        //for get the tv show
        viewModel.loadTvShows()



    }


}