package com.example.tvshowapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.tvshowapp.ViewModel.TvShowFavoriteViewModel
import com.example.tvshowapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inflate activity
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //check internet
        if( checkInternet()){
            //connected
        }else{
            Toast.makeText(this, "need internet connection", Toast.LENGTH_SHORT).show()
        }



        //Navigate to a destination
        val hostNav=  supportFragmentManager.findFragmentById(R.id.navigationHot) as NavHostFragment
        navController=hostNav.navController

        //instance viewModel
        val viewModelFavorite= ViewModelProvider(this).get(TvShowFavoriteViewModel::class.java)


        //search the bottomNavigationView
        bottomNavigationView=findViewById(R.id.bottomNavView)


        //controller of when show bottomNavigationView
        navController.addOnDestinationChangedListener() {_, destination,_ ->
            when (destination.id){
                R.id.profileFragment, R.id.listTvShowsFragment, R.id.listFavoritesFragment->
                {
                    bottomNavigationView.visibility= View.VISIBLE
                }
                else ->{
                    bottomNavigationView.visibility= View.GONE
                }

            }
        }
        //linking the navController with bottomNavigationView
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }

    private fun  checkInternet(): Boolean {
        // register activity with the connectivity manager service
        val connectivityManager = this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to R
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false
              // or greater we need to use the
              // NetworkCapabilities to check what type of
             // network has the internet connection
            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

}