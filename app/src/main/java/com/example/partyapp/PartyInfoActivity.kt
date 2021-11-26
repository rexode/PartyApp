package com.example.partyapp

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.party_layout.*

class PartyInfoActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.party_layout)

        button_join_party.setOnClickListener {
            //TODO: add user name to the recyclerview under "Participants:"

        }
    }


}