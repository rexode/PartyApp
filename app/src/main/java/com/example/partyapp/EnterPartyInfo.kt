package com.example.partyapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.enter_party_info.*

class EnterPartyInfo: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.enter_party_info)


   /*     button_done.setOnClickListener {
            //checking for empty field
            if (textedit_party_name.text.isNullOrEmpty()) {
                Toast.makeText(
                    this,
                    "Please enter a partyname",
                    Toast.LENGTH_SHORT
                ).show()
            } else if(textview_party_time.text.isNullOrEmpty()) {
                Toast.makeText(
                    this,
                    "Please enter a timeslot",
                    Toast.LENGTH_SHORT
                ).show()
            }else if(textview_party_date.text.isNullOrEmpty()){
                Toast.makeText(
                    this,
                    "Please enter a date",
                    Toast.LENGTH_SHORT
                ).show()
            } else if(textedit_party_location.text.isNullOrEmpty())  {
                Toast.makeText(
                    this,
                    "Please enter a location",
                    Toast.LENGTH_SHORT
                ).show()
            } else{
                //Todo DOTO: LiveData with AllParties and update the list
                // and return to allPartys

            }

        }

    */

    }

}