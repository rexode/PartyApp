package com.example.partyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.enter_name_fragment.*
import kotlinx.android.synthetic.main.enter_name_fragment.button_done
import kotlinx.android.synthetic.main.enter_partyinfo_fragment.*

class PartyAdding : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.enter_partyinfo_fragment)
        button_done
        textedit_party_name
        textedit_party_location

    }
}