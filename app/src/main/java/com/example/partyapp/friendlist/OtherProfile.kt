package com.example.partyapp.friendlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.partyapp.livedata.PartyViewModel
import com.example.partyapp.R
import com.example.partyapp.parties.Party
import com.example.partyapp.partydetails.FollowingPartiesAdapter
import com.example.partyapp.partydetails.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.my_profile_layout.*
import kotlinx.android.synthetic.main.others_profile_layout.*
import kotlinx.android.synthetic.main.others_profile_layout.recyclerviewAllParties


class OtherProfile: AppCompatActivity() {
    private lateinit var partyViewModel: PartyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.others_profile_layout)
        partyViewModel = ViewModelProvider(this).get(PartyViewModel::class.java)
        val userId=intent.getStringExtra("id")
        var user= User()
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

        var liveList:List<Party>
        partyViewModel.getUserParties(userId!!).observe(this,{list-> liveList=list
            val pardapter = FollowingPartiesAdapter(liveList)
            recyclerviewAllParties.adapter = pardapter
        })

        recyclerviewAllParties.layoutManager = LinearLayoutManager(this)
    }

    //also for go back button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}