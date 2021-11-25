package com.example.partyapp

import android.os.Bundle
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
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
            dismiss() }

        return rootView
    }
}