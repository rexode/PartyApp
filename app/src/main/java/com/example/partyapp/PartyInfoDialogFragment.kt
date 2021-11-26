package com.example.partyapp

import android.os.Bundle
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.enter_partyinfo_fragment.*
import kotlinx.android.synthetic.main.enter_partyinfo_fragment.view.*
import java.util.zip.Inflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.enter_name_fragment.*
import kotlinx.android.synthetic.main.enter_partyinfo_fragment.button_done


class PartyInfoDialogFragment: DialogFragment() {
    private lateinit var partyViewModel: PartyViewModel


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
                partyViewModel = ViewModelProvider(this).get(PartyViewModel::class.java)
                val party=Party(textedit_party_name.text.toString(),textedit_party_time.text.toString(),textedit_party_location.text.toString(),textedit_party_additionalInfo.text.toString())
                partyViewModel.addParty(party)
                dismiss()
            }

        }

        return rootView
    }
}