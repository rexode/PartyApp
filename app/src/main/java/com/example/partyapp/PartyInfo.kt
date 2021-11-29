package com.example.partyapp

import android.content.ContentValues.TAG
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.login_layout.*
import kotlinx.android.synthetic.main.party_layout.*
import kotlinx.android.synthetic.main.party_layout.view.*

class PartyInfo() : DialogFragment() {
    private lateinit var partyViewModel: PartyViewModel
    private lateinit var dbRef : DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        partyViewModel= ViewModelProvider(this).get(PartyViewModel::class.java)
        var rootView=inflater.inflate(R.layout.party_layout,container,false)


        rootView.textView_party_name.text = arguments?.get("name").toString()
        rootView.textView_party_time.text = arguments?.get("time").toString()
        rootView.textView_location.text= arguments?.get("location").toString()
        rootView.textView_insert_addInfo.text=arguments?.get("additionalInfo").toString()
        rootView.textView_date.text= arguments?.get("date").toString()

        return rootView
    }

}