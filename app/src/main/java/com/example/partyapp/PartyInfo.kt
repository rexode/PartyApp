package com.example.partyapp

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.enter_partyinfo_fragment.view.*
import kotlinx.android.synthetic.main.party_layout.*
import kotlinx.android.synthetic.main.party_layout.*
import kotlinx.android.synthetic.main.party_layout.view.*
import java.io.IOException
import java.util.*

class PartyInfo() : AppCompatActivity() {
    private lateinit var partyViewModel: PartyViewModel
    private val dummyParty: Party = Party("name","today","now","here","info")

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    var currentLocation: String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.party_layout)
        partyViewModel= ViewModelProvider(this).get(PartyViewModel::class.java)
        partyViewModel.findParty(intent.getStringExtra("id")).observe(this,{
            list->
            textView_party_name.text = list.get(0).name
            textView_party_time.text = list.get(0).time
            textView_insert_addInfo.text = list.get(0).AditionalInfo
            textView_location.text = list.get(0).location
            textView_date.text = list.get(0).date

        })
        var partyList = mutableListOf(
            Party("aId", "aName", "atime", "ahere"),
            Party("bId", "bName", "btime", "bhere"),
            Party("cId", "cName", "ctime", "chere"),
            Party("dId", "dName", "dtime", "dhere"),
            Party("eId", "eName", "etime", "ehere"),
        )
       val adapter = ParticipantAdapter(partyList)
        recyclerviewPartyInfo.adapter = adapter
        recyclerviewPartyInfo.layoutManager = LinearLayoutManager(this)


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        textView_location.setOnClickListener {

        var location: String = textView_location.text.toString()

                getLocation()
                DisplayTrack(location);
            }
        button_edit_party.setOnClickListener{
            Toast.makeText(this, "pressed edit party", Toast.LENGTH_LONG).show()
            //TODO open up edit party, paste infos from party --> change stuff
            //var dialog = PartyInfoDialogFragment()
            //dialog.show(supportFragmentManager, "customDialog")

        }
        button_join_party.setOnClickListener{
            //partyList.add(Party("eId", "eName", "etime", "ehere"))
            //adapter.notifyItemInserted(partyList.size -1)
        }

        button_edit_party.setOnClickListener {
            var dialog = PartyInfoDialogFragment2()
            dialog.show(supportFragmentManager, "customDialog") }

    }





    //open map-intent
    // We should maybe first ask for permission to get the location
    @SuppressLint("MissingPermission")
    fun getLocation() {
        //gets the users location
        fusedLocationProviderClient.lastLocation.addOnCompleteListener {
            var location: Location = it.getResult()
            if (location != null) {
                try {
                    var geocoder: Geocoder = Geocoder(this, Locale.getDefault())

                    var adresses: List<Address> = geocoder.getFromLocation(
                        location.latitude, location.longitude, 1
                    )
                    currentLocation = adresses[0].getAddressLine(0)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    fun DisplayTrack(location: String) {
        try {
            var uri: Uri = Uri.parse("https://www.google.co.in/maps/dir/$currentLocation/$location")
            var intent: Intent = Intent(Intent.ACTION_VIEW, uri)

            intent.setPackage("com.google.android.apps.maps")

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            var uri: Uri = Uri.parse(
                "https://play.google.com/store/apps/details?id=com.google.android.apps.maps$location"
            )
            var intent: Intent = Intent(Intent.ACTION_VIEW, uri)

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            startActivity(intent)
        }
    }



}