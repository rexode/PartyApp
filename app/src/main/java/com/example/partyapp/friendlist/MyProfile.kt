package com.example.partyapp.friendlist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.partyapp.R
import com.example.partyapp.livedata.PartyViewModel
import com.example.partyapp.livedata.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.my_profile_layout.*
import kotlinx.android.synthetic.main.others_profile_layout.*

class MyProfile: AppCompatActivity() {
    private lateinit var partyViewModel: PartyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_profile_layout)
        partyViewModel = ViewModelProvider(this).get(PartyViewModel::class.java)
        Toast.makeText(this,"aaaa",Toast.LENGTH_SHORT).show()

        actionBar?.setTitle("Profile")
        supportActionBar?.setTitle("Profile")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        partyViewModel.getUser(Firebase.auth.currentUser?.uid!!).observe(this,{
            user_number_of_followers.setText(it.name)
        })
    }

    //also for go back button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}