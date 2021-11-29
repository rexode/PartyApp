package com.example.partyapp

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.login_layout.*
import kotlinx.android.synthetic.main.party_layout.*
import kotlinx.android.synthetic.main.party_layout.view.*

class PartyInfo : DialogFragment() {
    private lateinit var partyViewModel: PartyViewModel
    private lateinit var dbRef : DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        partyViewModel= ViewModelProvider(this).get(PartyViewModel::class.java)
        var rootView=inflater.inflate(R.layout.party_layout,container,false)
        var party : Party? = partyViewModel.findParty(arguments?.getString("id"))

        rootView.textView_party_name.text = party?.name
        rootView.textView_party_time.text = party?.time
        rootView.textView_location.text= party?.location
        rootView.textView_insert_addInfo.text=party?.AditionalInfo
//      rootView.textView_date.text= party.date

        return rootView
    }

}