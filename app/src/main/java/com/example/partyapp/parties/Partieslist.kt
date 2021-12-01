package com.example.partyapp.parties

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.partyapp.partydetails.PartyInfoDialogFragment
import com.example.partyapp.R
import kotlinx.android.synthetic.main.all_partys_layout.*

class Partieslist : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_partys_layout)

        button_add_party.setOnClickListener {
            var dialog = PartyInfoDialogFragment()
            dialog.show(supportFragmentManager, "customDialog")
        }



    }

}