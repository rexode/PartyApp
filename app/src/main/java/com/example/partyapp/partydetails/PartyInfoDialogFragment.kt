package com.example.partyapp.partydetails

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.enter_partyinfo_fragment.*
import kotlinx.android.synthetic.main.enter_partyinfo_fragment.view.*
import androidx.lifecycle.ViewModelProvider
import com.example.partyapp.livedata.PartyViewModel
import com.example.partyapp.R
import com.example.partyapp.login.MainActivity
import com.example.partyapp.parties.Party
import java.text.SimpleDateFormat
import java.util.*


class PartyInfoDialogFragment: DialogFragment() {
    private lateinit var partyViewModel: PartyViewModel


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var rootView: View = inflater.inflate(R.layout.enter_partyinfo_fragment, container, false)

        //actionBar?.setTitle("Party Information")
        // supportActionBar?.setTitle("Party Information")
        var name = arguments?.getString("userName")
        var email = arguments?.getString("userEmail")
        var id = arguments?.getString("userId")


        // Date-picker
        val myCalender = Calendar.getInstance()
        val datePicker = DatePickerDialog
            .OnDateSetListener { view, year, month, dayOfMonth ->
                myCalender.set(Calendar.YEAR, year)
                myCalender.set(Calendar.MONDAY, month)
                myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLable(myCalender)
            }

        rootView.textview_party_date.setOnClickListener {
            activity?.let { it1 ->
                DatePickerDialog(
                    it1, datePicker, myCalender.get(Calendar.YEAR), myCalender.get(Calendar.MONTH), myCalender.get(Calendar.DAY_OF_MONTH)).show()
            }

        }

        // Time-picker
        rootView.textview_party_time.setOnClickListener{
            setTime()
        }




        rootView.button_done.setOnClickListener {

            //checking for empty field
            if (textedit_party_name.text.isNullOrEmpty()) {
                Toast.makeText(
                    activity,
                    "Please enter a partyname",
                    Toast.LENGTH_SHORT
                ).show()
            } else if(textview_party_time.text.isNullOrEmpty()) {
                Toast.makeText(
                    activity,
                    "Please enter a timeslot",
                    Toast.LENGTH_SHORT
                ).show()
            } else if(textview_party_date.text.isNullOrEmpty()){
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
                val party= Party(textedit_party_name.text.toString(),textview_party_date.text.toString(),textview_party_time.text.toString(),textedit_party_location.text.toString(),textedit_party_additionalInfo.text.toString(),id!!)
                val user= User(email,id,name)
                val newId=partyViewModel.addParty(party,user)
                //Toast.makeText(activity,user.id+user.email+user.name+newId,Toast.LENGTH_SHORT).show()
                partyViewModel.addParticipants(email!!, id!!, name!!, newId)

                dismiss()
            }

        }

        return rootView
    }


    // Dateformatter
    private fun updateLable(myCalender: Calendar) {
        val myFormat = "dd.MM.yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.GERMANY)
        textview_party_date.setText(sdf.format(myCalender.time))
    }

    // Timepicker -logic
    private fun setTime() {

        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog
            .OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                textview_party_time.text = SimpleDateFormat("HH:mm")
                    .format(cal.time)
            }
        TimePickerDialog(activity, timeSetListener, cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE), true).show()
    }



}