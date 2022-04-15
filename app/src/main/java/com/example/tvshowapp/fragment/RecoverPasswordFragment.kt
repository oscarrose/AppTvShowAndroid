package com.example.tvshowapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.tvshowapp.R
import com.example.tvshowapp.databinding.FragmentRecoverPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecoverPasswordFragment : Fragment() {
    //declare a instance
    lateinit var binding: FragmentRecoverPasswordBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentRecoverPasswordBinding.inflate(inflater, container, false)

        //for recover password
        binding.btRecoverPassword.setOnClickListener(){
            signUp();
        }


        //end inflate
        return binding.root
    }

    private fun validData():Boolean{
        var valid:Boolean
        if(binding.inputEmailRecover.text.isNotEmpty()){
            valid=true
        }else{
           binding.inputEmailRecover.error="enter an email"
            valid=false
        }
        return valid
    }

    private fun signUp() {
       if(validData()){
           val email=binding.inputEmailRecover.text.toString()
           Firebase.auth.sendPasswordResetEmail(email).addOnCompleteListener{
               if(it.isSuccessful){
                   Toast.makeText(requireContext(),"Email send", Toast.LENGTH_SHORT ).show()
               }
           }
           findNavController().navigate(R.id.loginFragment)
       }
    }

}