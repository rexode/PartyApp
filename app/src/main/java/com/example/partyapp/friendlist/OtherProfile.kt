package com.example.partyapp.friendlist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.partyapp.livedata.PartyViewModel
import com.example.partyapp.R
import com.example.partyapp.livedata.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.others_profile_layout.*


class OtherProfile: AppCompatActivity() {
    private lateinit var partyViewModel: PartyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.others_profile_layout)
        partyViewModel = ViewModelProvider(this).get(PartyViewModel::class.java)
        val userId=intent.getStringExtra("id")
        var user=User()
        partyViewModel.getUser(userId!!).observe(this,{
            user= User(it.email,it.id,it.name)
            user_name.setText(user.name)
        })
        val thisUserid= Firebase.auth.currentUser?.uid
        actionBar?.setTitle("Profile")
        supportActionBar?.setTitle("Profile")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        button_follower.setOnClickListener{
            if (userId != null) {
                    partyViewModel.addFollowing(user.email!!,user.id!!,user.name!!,thisUserid!!)



            }
        }
    }

    //also for go back button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}