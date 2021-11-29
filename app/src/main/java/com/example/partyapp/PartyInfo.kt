package com.example.partyapp

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.enter_partyinfo_fragment.view.*
import kotlinx.android.synthetic.main.party_layout.*
import kotlinx.android.synthetic.main.party_layout.*
import kotlinx.android.synthetic.main.party_layout.view.*
import java.io.IOException
import java.util.*

class PartyInfo : DialogFragment() {
    private lateinit var partyViewModel: PartyViewModel
    private lateinit var dbRef: DatabaseReference

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var currentLocation: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        partyViewModel = ViewModelProvider(this).get(PartyViewModel::class.java)
        var rootView = inflater.inflate(R.layout.party_layout, container, false)
        var party: Party? = partyViewModel.findParty(arguments?.getString("id"))

        //TODO if(user == creator) --> visible
        rootView.button_edit_party.visibility = View.VISIBLE

        rootView.textView_party_name.text = party?.name
        rootView.textView_party_time.text = party?.time
        rootView.textView_location.text = party?.location
        rootView.textView_insert_addInfo.text = party?.AditionalInfo
//      rootView.textView_date.text= party.date



        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)

        rootView.textView_location.setOnClickListener {

        var location: String = rootView.textView_location.text.toString()

                getLocation()
                DisplayTrack(location);
            }
        rootView.button_edit_party.setOnClickListener{
            Toast.makeText(context, "pressed edit party", Toast.LENGTH_LONG).show()
        }


        return rootView
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
                    var geocoder: Geocoder = Geocoder(activity, Locale.getDefault())

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