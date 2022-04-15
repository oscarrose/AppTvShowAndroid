package com.example.tvshowapp.fragment

import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.tvshowapp.R
import com.example.tvshowapp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {

    //declare an instance
    private lateinit var binding: FragmentLoginBinding
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentLoginBinding.inflate(inflater, container, false)


        // Initialize Firebase Auth
        auth = Firebase.auth


        //distinction of the fragments--- show registerFragment
        binding.ButtonSignup.setOnClickListener(){
            findNavController().navigate(R.id.registrerAccountFragment)
        }

        // show recoverPasswordFragment
        binding.ButtonForgetPassword.setOnClickListener(){
            findNavController().navigate(R.id.recoverPasswordFragment)
        }

        //Sign In
        binding.ButtonLogin.setOnClickListener(){
            signIn()
        }

        //end the inflate
        return binding.root
    }

    //function for validate the input
    private fun validData(): Boolean {
        var valid:Boolean;

        if (binding.inputUserName.text.isNotEmpty()){
            valid=true
        }else{
            binding.inputUserName.error = "enter an email";
            valid=false
        }

        if (binding.inputPass.text.isNotEmpty()){
            valid=true
        }else{
            binding.inputPass.error = "enter a password";
            valid=false
        }
        return valid

    }

    //clear the input the fragment
    private  fun clearInput(){
        binding.inputUserName.text.clear()
        binding.inputPass.text.clear()
    }

    // function for log in
    private fun signIn () {
        if (validData()) {
            auth.signInWithEmailAndPassword(
                binding.inputUserName.text.toString(),
                binding.inputPass.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    clearInput()
                    Toast.makeText(requireContext(),"Welcome", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.listTvShowsFragment)

                } else {
                    Toast.makeText(requireContext(), R.string.messageErrorLogin,
                        Toast.LENGTH_SHORT).show()
                }

            }
        }
    }



}