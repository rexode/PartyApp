package com.example.partyapp.partydetails

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.partyapp.livedata.PartyViewModel
import com.example.partyapp.parties.Party
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.party_layout.*
import java.io.IOException
import java.util.*
import com.example.partyapp.R
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size


class PartyInfo() : AppCompatActivity() {


    private lateinit var partyViewModel: PartyViewModel

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var currentLocation: String = ""

    private var name: String = ""
    private var date: String = ""
    private var time: String = ""
    private var location: String = ""
    private var addInfo: String = ""

    private  var owner: Boolean = false



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


        val sp: SharedPreferences = getSharedPreferences("FILE_NAME", MODE_PRIVATE)
        val uidRestored = sp.getString("key", "")

        var usrId = "Error"
        partyViewModel.findParty(intent.getStringExtra("id")).observe(this, { list ->
            usrId = list.get(0).creatorId.toString()

            if (usrId == uidRestored) owner = true


        })

        var partyId = intent.getStringExtra("id")
        var liveList: List<User>
        partyViewModel.getParticipants(intent.getStringExtra("id")!!).observe(this, { list ->
            liveList = list
            val adapter = ParticipantAdapter(liveList)
            recyclerviewPartyInfo.adapter = adapter
        })
        recyclerviewPartyInfo.layoutManager = LinearLayoutManager(this)


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        textView_location.setOnClickListener {

            var location: String = textView_location.text.toString()

            getLocation()
            DisplayTrack(location);
        }


        button_join_party.setOnClickListener {

            konfetti.build()
                .addColors(Color.parseColor("#DB7261"), Color.parseColor("#F0973A"), Color.parseColor("#6eadff"))
                .setDirection(0.0, 359.0)
                .setSpeed(4f, 10f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(Size(12, 5F))
                .setPosition(-50f, konfetti.width + 50f, -50f)
                .streamFor(300, 1000L)



            val sp: SharedPreferences = getSharedPreferences("FILE_NAME", MODE_PRIVATE)
            val uidRestored = sp.getString("key", "")
            val sp2: SharedPreferences = getSharedPreferences("FILE_NAME", MODE_PRIVATE)
            val nameRestored = sp2.getString("username", "")
            val sp3: SharedPreferences = getSharedPreferences("FILE_NAME", MODE_PRIVATE)
            val emailRestored = sp3.getString("email", "")
            partyViewModel.addParticipants(
                emailRestored!!,
                uidRestored!!,
                nameRestored!!,
                intent.getStringExtra("id")!!
            )
        }
    }


    //open map-intent
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
        inflator.inflate(com.example.partyapp.R.menu.dot_menu_partylayout, menu)

        return true
    }


// LOGOUT AND PROFILE MENU


    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        if(!owner){
            menu!!.removeItem(R.id.menu_edit)
            menu.removeItem(R.id.menu_delete)
        }
        if(owner){
            menu!!.removeItem(R.id.menu_leave)
            button_join_party.visibility = View.GONE
        }


        return super.onPrepareOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            com.example.partyapp.R.id.menu_leave -> {

                val sp: SharedPreferences = getSharedPreferences("FILE_NAME", MODE_PRIVATE)
                val uidRestored = sp.getString("key", "")
                partyViewModel.removeParticipant(uidRestored!!, intent.getStringExtra("id")!!)
            }


            com.example.partyapp.R.id.menu_edit -> {
                var partyId = intent.getStringExtra("id")


                var dialog = PartyInfoDialogFragment2(partyId, name, time, date, location, addInfo)
                dialog.show(supportFragmentManager, "customDialog")

            }

            com.example.partyapp.R.id.menu_delete -> {

                val id=intent.getStringExtra("id")
                partyViewModel.deleteParty(id!!)
            }

        }

        return super.onOptionsItemSelected(item)
    }


}