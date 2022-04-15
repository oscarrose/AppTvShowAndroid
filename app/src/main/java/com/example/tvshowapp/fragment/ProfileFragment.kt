package com.example.tvshowapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tvshowapp.R
import com.example.tvshowapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {
    //Declare an instance
    lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentProfileBinding.inflate(inflater, container, false)

        // Initialize Firebase Auth
        auth = Firebase.auth


        //invoke fun for load data user
        //get user current
        val userCurrent=auth.currentUser
        val uid=userCurrent!!.uid

        initUser(uid)

        //sign Out
        binding.buttonLogout.setOnClickListener(){
            auth.signOut().also {
                findNavController().navigate(R.id.loginFragment)
            }
        }

        //for edit an user
        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.opcionEditProfile-> {
                    findNavController().navigate(R.id.editProfileFragment2)
                   true
                }
                else -> false
            }
        }

        //end inflate
        return binding.root
    }

    //load the date the user
    private fun initUser(uid:String){
        //search y show the date the user
        db.collection("users").document(uid)
            .get().addOnSuccessListener {
                binding.showEmail.text = it.get("email").toString()
                binding.showName.text= "${it.get("name").toString()} ${it.get("lastName").toString()}"
                binding.txtViewAddress.text=it.get("address").toString()
                binding.txtViewPhone.text=it.get("phone").toString()
                binding.txtViewDate.text=it.get("dateOfBrith").toString()
                binding.txtViewCountry.text=it.get("country").toString()
                binding.txtViewCity.text=it.get("city").toString()
                binding.txtViewGender.text=it.get("gender").toString()

        }
    }
}