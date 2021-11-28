package com.example.partyapp

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.test_location_route_layout.*
import java.io.IOException
import java.util.*

class TestLocationRoute: AppCompatActivity() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var currentLocation: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_location_route_layout)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        location_route_button.setOnClickListener {
            var location: String = enter_location.text.toString()

            if (location.equals("")) {
                Toast.makeText(
                    this, "Enter a location", Toast.LENGTH_SHORT
                ).show()
            } else {
                getLocation()
                DisplayTrack(location);
            }

        }

    }

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