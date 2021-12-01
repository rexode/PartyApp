package com.example.partyapp.friendlist

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.partyapp.R
import com.example.partyapp.livedata.PartyViewModel
import com.example.partyapp.parties.Party
import com.example.partyapp.partydetails.FollowingPartiesAdapter
import com.example.partyapp.partydetails.FriendsListAdapter
import com.example.partyapp.partydetails.ParticipantAdapter
import com.example.partyapp.partydetails.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.friends_list_layout.*
import kotlinx.android.synthetic.main.my_profile_layout.*
import kotlinx.android.synthetic.main.party_layout.*
import java.io.FileNotFoundException
import java.io.IOException

class MyProfile: AppCompatActivity() {

    private lateinit var partyViewModel: PartyViewModel
    var SELECT_PHOTO = 1
    var uri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_profile_layout)
        partyViewModel = ViewModelProvider(this).get(PartyViewModel::class.java)


        //var liveList: List<User>
        //partyViewModel.getParticipants(intent.getStringExtra("id")!!).observe(this, { list ->
            //liveList = list
//        })
        var livList:List<User>
        partyViewModel.getFollowings(Firebase.auth.uid!!).observe(this,{list-> livList=list
            val adapter = FriendsListAdapter(livList)
            recyclerviewFollowers.adapter = adapter
        })

        recyclerviewFollowers.layoutManager = LinearLayoutManager(this)
        //recyclerviewFollowing.adapter = adapter
        //recyclerviewFollowing.layoutManager = LinearLayoutManager(this)
        var liveList:List<Party>
        partyViewModel.getUserParties(Firebase.auth.uid!!).observe(this,{list-> liveList=list
            val pardapter = FollowingPartiesAdapter(liveList)
            recyclerviewAllParties.adapter = pardapter

        })

        recyclerviewAllParties.layoutManager = LinearLayoutManager(this)


        actionBar?.setTitle("Profile")
        supportActionBar?.setTitle("Profile")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        partyViewModel.getUser(Firebase.auth.currentUser?.uid!!).observe(this,{

            my_user_name.setText(it.name)

        })


        profilepic.setOnClickListener {
            var intent : Intent =  Intent(Intent.ACTION_PICK)
            intent.setType("image/*")
            startActivityForResult(intent, SELECT_PHOTO)

        }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if(requestCode == SELECT_PHOTO
            && resultCode == RESULT_OK
            && data != null
            && data.data != null){


            uri = data.data
            try{
                var bitmap:  Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                profilepic.setImageBitmap(bitmap)

            } catch (e: FileNotFoundException){
                e.printStackTrace()
            } catch (e: IOException){
                e.printStackTrace()
            }

        }
    }

    //also for go back button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}