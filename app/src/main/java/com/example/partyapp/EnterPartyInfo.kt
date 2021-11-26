package com.example.partyapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.enter_party_info.*
import java.text.SimpleDateFormat
import java.util.*

class EnterPartyInfo: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.enter_party_info)



        // Date-picker
        val myCalender = Calendar.getInstance()
        val datePicker = DatePickerDialog
            .OnDateSetListener { view, year, month, dayOfMonth ->
                myCalender.set(Calendar.YEAR, year)
                myCalender.set(Calendar.MONDAY, month)
                myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLable(myCalender)
            }

        textview_party_date.setOnClickListener {
            DatePickerDialog(this
                , datePicker
                , myCalender.get(Calendar.YEAR)
                , myCalender.get(Calendar.MONTH)
                , myCalender.get(Calendar.DAY_OF_MONTH)).show()

        }

        // Time-picker
        textview_party_time.setOnClickListener{
            setTime()
        }



        button_done.setOnClickListener {
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
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE), true).show()
    }


}