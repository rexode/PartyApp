package com.example.partyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.enter_name_fragment.*
import kotlinx.android.synthetic.main.enter_name_fragment.button_done
import kotlinx.android.synthetic.main.enter_partyinfo_fragment.*
import androidx.lifecycle.ViewModelProviders


import java.util.EnumSet.of

class PartyAdding : AppCompatActivity() {
    private lateinit var partyViewModel: PartyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.enter_partyinfo_fragment)

        //val id =intent.getStringExtra("id")
        val factory = PartyViewModelFactory()
        val intent = Intent(this, AllParties::class.java)

        partyViewModel = ViewModelProvider(this).get(PartyViewModel::class.java)

        button_done.setOnClickListener{
            //val party=Party(textedit_party_name.text.toString(),"mañana",textview_party_time.text.toString(),textedit_party_location.text.toString(),textedit_party_additionalInfo.text.toString())
            val party=Party("a","B","C","D","E")
            partyViewModel.addParty(party)
            //intent.putExtra("UserId",)

            startActivity(intent)
        }


    }
}