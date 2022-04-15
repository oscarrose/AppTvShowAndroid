package com.example.tvshowapp.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tvshowapp.R
import com.example.tvshowapp.databinding.FragmentEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class EditProfileFragment : Fragment() {

    //Declare an instance
    lateinit var binding: FragmentEditProfileBinding
    val calender= Calendar.getInstance()
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
        binding= FragmentEditProfileBinding.inflate(inflater,container, false)

        // Initialize Firebase Auth
        auth = Firebase.auth


        //invoke fun for load data user
        //get user current
        val userCurrent=auth.currentUser
        val uid=userCurrent!!.uid

        initUser(uid)

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

        //for update  info the user
        binding.bottomSavePerfile.setOnClickListener(){
            updateDataUser(uid)

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

    //load the date the user
    private fun initUser(uid:String){
        //search y show the date the user
        db.collection("users").document(uid)
            .get().addOnSuccessListener {
                binding.inputAddress.setText(it.get("address").toString())
                binding.inputPhone.setText(it.get("phone").toString())
                binding.inputDate.setText(it.get("dateOfBrith").toString())
                binding.inputCountry.setText(it.get("country").toString())
                binding.inputCity.setText(it.get("city").toString())
                binding.editName.setText(it.get("name").toString())
                binding.editLastName.setText(it.get("lastName").toString())
                binding.editEmail.setText(it.get("email").toString())
                binding.editGender.setText(it.get("gender").toString())
            }

    }

    //fun for update profile user
    private fun updateDataUser(uid:String) {
       if(validData()){
           db.collection("users").document(uid).set(
               hashMapOf(
                   "phone" to binding.inputPhone.text.toString(),
                   "dateOfBrith" to binding.inputDate.text.toString(),
                   "country" to binding.inputCountry.text.toString(),
                   "city" to binding.inputCity.text.toString(),
                   "address" to binding.inputAddress.text.toString(),
                   "name" to binding.editName.text.toString(),
                   "lastName" to binding.editLastName.text.toString(),
                   "email" to binding.editEmail.text.toString(),
                   "gender" to binding.editGender.text.toString()

               )
           )
           Toast.makeText(requireContext(), "Update success",
               Toast.LENGTH_SHORT).show()
           findNavController().navigate(R.id.profileFragment)

       }else{
           //failed register
           Toast.makeText(requireContext(), "Update failed.",
               Toast.LENGTH_SHORT).show()

       }
    }



}
