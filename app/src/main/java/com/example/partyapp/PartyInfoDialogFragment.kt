package com.example.partyapp

import android.os.Bundle
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.enter_partyinfo_fragment.*
import kotlinx.android.synthetic.main.enter_partyinfo_fragment.view.*
import java.util.zip.Inflater
import androidx.appcompat.app.AppCompatActivity


class PartyInfoDialogFragment: DialogFragment() {


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var rootView: View = inflater.inflate(R.layout.enter_partyinfo_fragment, container, false)



        rootView.button_done.setOnClickListener {

            //checking for empty field

            if (textedit_party_name.text.isNullOrEmpty()) {
                Toast.makeText(
                    activity,
                    "Please enter a partyname",
                    Toast.LENGTH_SHORT
                ).show()
            } else if(textedit_party_time.text.isNullOrEmpty()){
                Toast.makeText(
                    activity,
                    "Please enter a timeslot",
                    Toast.LENGTH_SHORT
                ).show()
            } else if(textedit_party_location.text.isNullOrEmpty())  {
                Toast.makeText(
                    activity,
                    "Please enter a location",
                    Toast.LENGTH_SHORT
                ).show()
            } else{
                //Todo DOTO: LiveData with AllParties and update the list

                dismiss()
            }

        }

        return rootView
    }


}