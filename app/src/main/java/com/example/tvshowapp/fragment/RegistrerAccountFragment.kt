package com.example.tvshowapp.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tvshowapp.R
import com.example.tvshowapp.databinding.FragmentRegistrerAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class RegistrerAccountFragment : Fragment() {

    //Declare an instance
    lateinit var binding: FragmentRegistrerAccountBinding
    val calender= Calendar.getInstance()
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

 //  @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentRegistrerAccountBinding.inflate(inflater, container,false)

        //creating the info the datePicker
        val datePicker=DatePickerDialog.OnDateSetListener{ view,year,month,dayOfMoth->
            calender.set(Calendar.YEAR, year)
            calender.set(Calendar.MONTH,month)
            calender.set(Calendar.DAY_OF_MONTH, dayOfMoth)
            writeInputDate(calender)
        }


        //get the date the user
        binding.inputDate.setOnClickListener(){
            DatePickerDialog(requireContext(),datePicker, calender.get(Calendar.YEAR),
            calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH)).show()

        }

     // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(requireContext(), R.array.gender,
         android.R.layout.simple_spinner_item).also { adapter ->
             // Specify the layout to use when the list of choices appears
             adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
             // Apply the adapter to the spinner
             binding.selectGender.adapter = adapter
        }

        // Initialize Firebase Auth
        auth = Firebase.auth

        //action the register
        binding.buttonSave.setOnClickListener(){
            registerCountUser()
        }

        //end the inflate
        return binding.root
    }

    //fun for write the date in inputDate
    private fun writeInputDate(calender:Calendar){
        val myFormat="dd-MM-yyyy"
        val giveFormat= SimpleDateFormat(myFormat, Locale.US)
        binding.inputDate.setText(giveFormat.format(calender.time))
    }

    // function for valid of data
    private fun validData():Boolean{
        var valid:Boolean=true;

        if(binding.inputName.text!!.isEmpty()){
            binding.layoutName.error = getString(R.string.messageError)
            valid=false
        }else{
            binding.layoutName.error = null
            valid=true
        }

        if(binding.inputLastName.text!!.isEmpty()){
            binding.layoutLastname.error = getString(R.string.messageError)
            valid=false
        }else{
            binding.layoutLastname.error = null
            valid=true
        }
        if(binding.inputEmail.text!!.isEmpty()){
            binding.layoutEmail.error = getString(R.string.messageError)
            valid=false
        }else{
            binding.layoutEmail.error = null
            valid=true
        }

        if(binding.inputPassword.text!!.isEmpty()){
            binding.inputPass.error = getString(R.string.messageError)
            valid=false
        }else{
            binding.inputPass.error = null
            valid=true
        }
        if(binding.inputDate.text!!.isEmpty()){
            binding.layoutDate.error = getString(R.string.messageError)
            valid=false
        }else{
            binding.layoutDate.error = null
            valid=true
        }

        if(binding.inputCountry.text!!.isEmpty()){
            binding.layoutCountry.error = getString(R.string.messageError)
            valid=false
        }else{
            binding.layoutCountry.error = null
            valid=true
        }

        if(binding.inputCity.text!!.isEmpty()){
            binding.layoutcity.error = getString(R.string.messageError)
            valid=false
        }else{
            binding.layoutcity.error = null
            valid=true
        }

        if(binding.inputAddress.text!!.isEmpty()){
            binding.layoutAddress.error = getString(R.string.messageError)
            valid=false
        }else{
            binding.layoutAddress.error = null
            valid=true
        }

        return valid
    }


    //save the data the user register
    private fun saveDataUser(idUser:String, email:String){

        // Add document with a IdUser
        db.collection("users").document(idUser).set(
            hashMapOf("name" to binding.inputName.text.toString(),
                "lastName" to binding.inputLastName.text.toString(),
                "email" to email,
                "phone" to binding.inputPhone.text.toString(),
                "dateOfBrith" to binding.inputDate.text.toString(),
                "gender"  to binding.selectGender.selectedItem.toString(),
                "country" to binding.inputCountry.text.toString(),
                "city" to binding.inputCity.text.toString(),
                "address" to binding.inputAddress.text.toString()

            )
        )


    }

    //function for register user
    private fun registerCountUser () {
        if (validData()) {
            auth.createUserWithEmailAndPassword(
                binding.inputEmail.text.toString(),
                binding.inputPassword.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    //save of data user saveUsers() //show fragment profile
                    val user = auth.currentUser
                     val uidUser= user!!.uid
                    val email=user.email
                    //invoke fun for save data the users
                    saveDataUser(uidUser,email!!)
                    Toast.makeText(requireContext(), "Authentication success",
                        Toast.LENGTH_SHORT).show()
                   findNavController().navigate(R.id.profileFragment)
                } else {
                    //failed register
                    Toast.makeText(requireContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

}