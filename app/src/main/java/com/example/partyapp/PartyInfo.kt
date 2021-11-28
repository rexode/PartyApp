package com.example.partyapp

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
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
        var rootView=inflater.inflate(R.layout.party_layout,container,false)
        dbRef= (FirebaseDatabase.getInstance())!!.getReference("Parties")
        dbRef.child(arguments?.getString("id")!!).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val party = dataSnapshot.getValue(Party::class.java)

                //Check for null
                if (party == null) {
                    Log.e(TAG, "party data is null")
                    return
                }
                Log.e(
                    TAG, "party data is changed!" + party.name + "," + party.time + "," +
                            party.location + "," + party.AditionalInfo
                )

                //Display newly updated name and email
                rootView.textView_party_name.text = party.name
                rootView.textView_party_time.text = party.time
                rootView.textView_location.text= party.location
                rootView.textView_insert_addInfo.text=party.AditionalInfo
//                rootView.textView_date.text= party.date
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return rootView
    }

}