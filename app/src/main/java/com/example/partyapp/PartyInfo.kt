package com.example.partyapp

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.all_partys_layout.*
import kotlinx.android.synthetic.main.enter_partyinfo_fragment.view.*
import kotlinx.android.synthetic.main.party_layout.*
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

        actionBar?.setTitle("Party Info")
        supportActionBar?.setTitle("Party Info")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var name: String = ""
        var date: String= ""
        var time :String= ""
        var location : String= ""
        var addInfo : String= ""

        partyViewModel= ViewModelProvider(this).get(PartyViewModel::class.java)
        partyViewModel.findParty(intent.getStringExtra("id")).observe(this,{
            list->
            textView_party_name.text = list.get(0).name
            textView_party_time.text = list.get(0).time
            textView_insert_addInfo.text = list.get(0).AditionalInfo
            textView_location.text = list.get(0).location
            textView_date.text = list.get(0).date

            name = list.get(0).name.toString()
            time = list.get(0).time.toString()
            addInfo = list.get(0).AditionalInfo.toString()
            location= list.get(0).location.toString()
            date =list.get(0).date.toString()

        })
        var partyList = mutableListOf(
            Party("aId", "aName", "atime", "ahere"),
            Party("bId", "bName", "btime", "bhere"),
            Party("cId", "cName", "ctime", "chere"),
            Party("dId", "dName", "dtime", "dhere"),
            Party("eId", "eName", "etime", "ehere"),
        )
        var liveList:List<User>
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


        button_join_party.setOnClickListener{
            val sp: SharedPreferences = getSharedPreferences("FILE_NAME", MODE_PRIVATE)
            val uidRestored = sp.getString("key", "")
            val sp2: SharedPreferences = getSharedPreferences("FILE_NAME", MODE_PRIVATE)
            val nameRestored = sp2.getString("username", "")
            val sp3: SharedPreferences = getSharedPreferences("FILE_NAME", MODE_PRIVATE)
            val emailRestored = sp3.getString("email", "")
            partyViewModel.addParticipants(emailRestored!!, uidRestored!!, nameRestored!!, intent.getStringExtra("id")!!)
            //partyViewModel.addParticipants()
        }
        button_leave.setOnClickListener{
            //NOT TESTED YEEEEEEEEEEEEEET
            val sp: SharedPreferences = getSharedPreferences("FILE_NAME", MODE_PRIVATE)
            val uidRestored = sp.getString("key", "")
            //Toast.makeText(this, "jmGgeoscPYgjjqzW49sjLMx7HJv2",Toast.LENGTH_LONG).show()
            partyViewModel.removeParticipant(uidRestored!!, intent.getStringExtra("id")!!)
        }

        button_edit_party.setOnClickListener {

            var partyId = intent.getStringExtra("id")

           /* val bundle = Bundle()
            bundle.putString("edttext", "From Activity")
            // set Fragmentclass Arguments
            // set Fragmentclass Arguments
            val fragobj = PartyInfoDialogFragment2(partyId)
            fragobj.setArguments(bundle)

            */


           /*  val intent2 = Intent(it.context, PartyInfoDialogFragment2::class.java)
            intent2.putExtra("partyId",partyId )
            it.context.startActivity(intent2)

            val sp: SharedPreferences = getSharedPreferences("FILE_NAME", MODE_PRIVATE)
            val edit : SharedPreferences.Editor = sp.edit()
            edit.putString("partyId", partyId)
            edit.apply()

            */

            Toast.makeText(this, partyId, Toast.LENGTH_SHORT).show()


            var dialog = PartyInfoDialogFragment2(partyId,name,time, date, location, addInfo)
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

    //also for go back button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}