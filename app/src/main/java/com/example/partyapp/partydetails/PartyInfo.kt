package com.example.partyapp.partydetails

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.partyapp.livedata.PartyViewModel
import com.example.partyapp.R
import com.example.partyapp.livedata.User
import com.example.partyapp.parties.Party
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.party_layout.*
import java.io.IOException
import java.util.*

class PartyInfo() : AppCompatActivity() {
    private lateinit var partyViewModel: PartyViewModel
    private val dummyParty: Party = Party("name", "today", "now", "here", "info")

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    var currentLocation: String = ""

    var name: String = ""
    var date: String = ""
    var time: String = ""
    var location: String = ""
    var addInfo: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.party_layout)

        actionBar?.setTitle("Party Info")
        supportActionBar?.setTitle("Party Info")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        partyViewModel = ViewModelProvider(this).get(PartyViewModel::class.java)
        partyViewModel.findParty(intent.getStringExtra("id")).observe(this, { list ->
            textView_party_name.text = list.get(0).name
            textView_party_time.text = list.get(0).time
            textView_insert_addInfo.text = list.get(0).AditionalInfo
            textView_location.text = list.get(0).location
            textView_date.text = list.get(0).date

            name = list.get(0).name.toString()
            time = list.get(0).time.toString()
            addInfo = list.get(0).AditionalInfo.toString()
            location = list.get(0).location.toString()
            date = list.get(0).date.toString()

        })
        var partyList = mutableListOf(
            Party("aId", "aName", "atime", "ahere"),
            Party("bId", "bName", "btime", "bhere"),
            Party("cId", "cName", "ctime", "chere"),
            Party("dId", "dName", "dtime", "dhere"),
            Party("eId", "eName", "etime", "ehere"),
        )
        var liveList: List<User>
        partyViewModel.getParticipants(intent.getStringExtra("id")!!).observe(this, { list ->
            liveList = list
            val adapter = ParticipantAdapter(liveList)
            recyclerviewPartyInfo.adapter = adapter
        })
        //val adapter = ParticipantAdapter(partyList)
        recyclerviewPartyInfo.layoutManager = LinearLayoutManager(this)
        //recyclerviewPartyInfo.adapter = adapter
        //recyclerviewPartyInfo.layoutManager = LinearLayoutManager(this)


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        textView_location.setOnClickListener {

            var location: String = textView_location.text.toString()

            getLocation()
            DisplayTrack(location);
        }


        button_join_party.setOnClickListener {
            val sp: SharedPreferences = getSharedPreferences("FILE_NAME", MODE_PRIVATE)
            val uidRestored = sp.getString("key", "")
            val sp2: SharedPreferences = getSharedPreferences("FILE_NAME", MODE_PRIVATE)
            val nameRestored = sp2.getString("username", "")
            val sp3: SharedPreferences = getSharedPreferences("FILE_NAME", MODE_PRIVATE)
            val emailRestored = sp3.getString("email", "")
           // Toast.makeText(this, emailRestored, Toast.LENGTH_SHORT).show()
            partyViewModel.addParticipants(
                emailRestored!!,
                uidRestored!!,
                nameRestored!!,
                intent.getStringExtra("id")!!
            )
            //partyViewModel.addParticipants()
        }


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

    //also for go back button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    // Menu in tool-bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflator: MenuInflater = menuInflater
        inflator.inflate(R.menu.dot_menu_partylayout, menu)
        return true
    }


// **********************************LOGOUT AND PROFILE MENU********************************************************************************

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.leave -> {

                //NOT TESTED YEEEEEEEEEEEEEET
                val sp: SharedPreferences = getSharedPreferences("FILE_NAME", MODE_PRIVATE)
                val uidRestored = sp.getString("key", "")
                //Toast.makeText(this, "jmGgeoscPYgjjqzW49sjLMx7HJv2",Toast.LENGTH_LONG).show()
                partyViewModel.removeParticipant(uidRestored!!, intent.getStringExtra("id")!!)
            }


            R.id.edit -> {

                var partyId = intent.getStringExtra("id")


                Toast.makeText(this, partyId, Toast.LENGTH_SHORT).show()


                var dialog = PartyInfoDialogFragment2(partyId, name, time, date, location, addInfo)
                dialog.show(supportFragmentManager, "customDialog")

            }

            R.id.delete -> {

                partyViewModel.findParty(intent.getStringExtra("id")!!)
                var db = FirebaseFirestore.getInstance()
                db.collection("Parties").document(intent.getStringExtra("id")!!).delete()

            }

        }

        return super.onOptionsItemSelected(item)
    }
}