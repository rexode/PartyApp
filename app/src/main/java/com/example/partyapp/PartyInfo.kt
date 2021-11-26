package com.example.partyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.login_layout.*
import kotlinx.android.synthetic.main.party_layout.*

class PartyInfo : AppCompatActivity() {
    private lateinit var partyViewModel: PartyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.party_layout)
        textView_party_name
        textView_party_time
        textView_location
        textView_insert_addInfo

    }
}