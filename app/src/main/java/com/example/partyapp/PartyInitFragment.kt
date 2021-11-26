package com.example.partyapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
//import kotlinx.android.synthetic.main.enter_partyinfo_fragment.view.*

class PartyInitFragment : DialogFragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.enter_partyinfo_fragment, container,false)
//        not sure if this is correct, i checked your implementation but it had errors
        var btnDone= rootView.findViewById<Button>(R.id.button_done)

        btnDone.setOnClickListener {

            //TODO: save data from party - prolly in database
            //TODO: add an button_with_partyname and
            //      set button_with_partyname.text to textedit_party_name.text

            dismiss()

        }


        return rootView
    }

}