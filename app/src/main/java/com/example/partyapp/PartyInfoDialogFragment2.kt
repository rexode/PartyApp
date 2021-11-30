package com.example.partyapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.enter_partyinfo_fragment.view.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.enter_partyinfo_fragment.textedit_party_additionalInfo
import kotlinx.android.synthetic.main.enter_partyinfo_fragment.textedit_party_location
import kotlinx.android.synthetic.main.enter_partyinfo_fragment.textedit_party_name
import kotlinx.android.synthetic.main.enter_partyinfo_fragment.textview_party_date
import kotlinx.android.synthetic.main.enter_partyinfo_fragment.textview_party_time
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import android.text.Editable



class PartyInfoDialogFragment2(
    partyId: String?,
    name: String,
    time: String,
    date: String,
    location: String,
    addInfo: String
) : DialogFragment() {

private var name = name
private var date = date
private var time = time
private var location = location
private var addInfo = addInfo

    private lateinit var partyViewModel: PartyViewModel

    private var db= FirebaseFirestore.getInstance()

    private val partyCollectionRef = FirebaseFirestore.getInstance().collection("Parties")


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {



        var rootView: View = inflater.inflate(R.layout.enter_partyinfo_fragment, container, false)



        rootView.textedit_party_name.text = Editable.Factory.getInstance().newEditable(name)
        rootView.textview_party_date.text = date
        rootView.textview_party_time.text = time
        rootView.textedit_party_location.text =
            Editable.Factory.getInstance().newEditable(location)
         rootView.textedit_party_additionalInfo.text =
            Editable.Factory.getInstance().newEditable(addInfo)



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

                val oldParty = getOldParty()
                val newPartyMap = getNewPartyMap()
                updateParty(oldParty,newPartyMap)

                dismiss()
            }

        }

        return rootView
    }



    // *********************************************************************************************************************


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




    fun getOldParty(): Party {


        return Party(name, date, time, location, addInfo)
    }

        fun getNewPartyMap(): Map<String, Any> { // from enter_partyinfo_fragment
            val name = textedit_party_name.text.toString()
            val date = textview_party_date.text.toString()
            val time = textview_party_time.text.toString()
            val location = textedit_party_location.text.toString()
            val addInfo = textedit_party_additionalInfo.text.toString()
            val map = mutableMapOf<String, Any>()

            map["name"] = name
            map["date"] = date
            map["time"] = time
            map["date"] = date
            map["location"] = location
            map["aditionalInfo"] = addInfo

            return map
        }

        private fun updateParty(party: Party, newPartyMap: Map<String, Any>) =
            CoroutineScope(Dispatchers.IO).launch {
                val partyQuery = partyCollectionRef
                    .whereEqualTo("name", party.name)
                    .whereEqualTo("date", party.date)
                    .whereEqualTo("time", party.time)
                    .whereEqualTo("location", party.location)
                    .whereEqualTo("aditionalInfo", party.AditionalInfo)
                    .get().await()

                if (partyQuery.documents.isNotEmpty()) {
                    for (document in partyQuery) {
                        try {
                            partyCollectionRef.document(document.id).set(
                                newPartyMap,
                                SetOptions.merge()
                            ).await()
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    activity, e.message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }


                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            activity, "No party matched the query",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            }


    }