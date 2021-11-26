package com.example.partyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        val id =intent.getStringExtra("email")
        val factory = PartyViewModelFactory()
        val intent = Intent(this, PartyInfo::class.java)
        partyViewModel = ViewModelProviders.of(this, factory).get(PartyViewModel::class.java)
        button_done
        textedit_party_name
        textedit_party_location
        textedit_party_additionalInfo
        button_done.setOnClickListener{

            val party=Party(id,textedit_party_name.text.toString(),textedit_party_time.text.toString(),textedit_party_location.text.toString(),textedit_party_additionalInfo.text.toString())
            partyViewModel.addParty(party)
            intent.putExtra("UserId",id)

            startActivity(intent)
        }
    }
}